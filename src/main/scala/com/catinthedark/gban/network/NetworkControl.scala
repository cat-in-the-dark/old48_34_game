package com.catinthedark.gban.network

import java.util.concurrent.ConcurrentLinkedQueue

import com.catinthedark.gban.common.Const
import com.catinthedark.lib.Pipe
import org.zeromq.ZMQ
import org.zeromq.ZMQ.{Socket, Poller, PollItem}

trait NetworkControl extends Runnable {
  var isConnected: Option[Unit] = None

  val MOVE_PREFIX = "MOVE"
  val SHOOT_PREFIX = "SHOOT"
  val ILOOSE_PREFIX = "ILOOSE"
  val IWON_PREFIX = "IWON"
  val HELLO_PREFIX = "HELLO"
  val PROGRESS_PREFIX = "PROGRESS"
  val ALIVE_PREFIX = "ALIVE"

  val buffer = new ConcurrentLinkedQueue[String]()
  val bufferIn = new ConcurrentLinkedQueue[() => Unit]()

  val onMovePipe = new Pipe[(Float, Boolean)]()
  val onShootPipe = new Pipe[Boolean]()
  val onILoosePipe = new Pipe[Unit]()
  val onIWonPipe = new Pipe[Unit]()
  val onProgressPipe = new Pipe[Int]()
  val onAlivePipe = new Pipe[Unit]()

  def onMove(msg: (Float, Boolean)) = bufferIn.add(() => onMovePipe(msg))
  def onShoot(msg: Boolean) = bufferIn.add(() => onShootPipe(msg))
  def onILoose() = bufferIn.add(() => onILoosePipe())
  def onIWon() = bufferIn.add(() => onIWonPipe())
  def onProgress(msg: Int) = bufferIn.add(() => onProgressPipe(msg))
  def onAlive() = bufferIn.add(() => onAlivePipe())
  
  def onHello(pushSocket: Socket) = println("Received hello package")

  def move(x: Float, standUp: Boolean): Unit = {
    buffer.add(s"$MOVE_PREFIX:$x;$standUp")
  }

  def shoot(exactly: Boolean): Unit = {
    buffer.add(s"$SHOOT_PREFIX:$exactly")
  }

  def iLoose(): Unit = {
    buffer.add(s"$ILOOSE_PREFIX:")
  }

  def iWon(): Unit = {
    buffer.add(s"$IWON_PREFIX:")
  }

  def progress(progress: Int): Unit = {
    buffer.add(s"$PROGRESS_PREFIX:$progress")
  }
  def iAlive(): Unit = {
    buffer.add(s"$ALIVE_PREFIX:")
  }

  def processIn() = {
    while(!bufferIn.isEmpty)
      bufferIn.poll()()
  }

  def isServer: Boolean
  
  def work(pushSocket: Socket, pullSocket: Socket): Unit = {
    val pollItems = Array(new PollItem(pullSocket, Poller.POLLIN), new PollItem(pushSocket, Poller.POLLOUT))
    var shouldStop: Boolean = false
    var detectedGameEnd: Boolean = false

    while (!shouldStop && !Thread.currentThread().isInterrupted) {
      try {
        ZMQ.poll(pollItems, Const.pollTimeout)
        if (pollItems(0).isReadable) {
          val rawData = pullSocket.recvStr()
          println(s"Received data $rawData")
          val data = rawData.split(":")

          data(0) match {
            case MOVE_PREFIX =>
              val attrs = data(1).split(";")
              val x = attrs(0).toFloat
              val standUp = attrs(1).toBoolean
              onMove(x, standUp)
            case SHOOT_PREFIX =>
              val attrs = data(1).split(";")
              val exactly = attrs(0).toBoolean
              onShoot(exactly)
            case ILOOSE_PREFIX =>
              detectedGameEnd = true
              onILoose()
            case IWON_PREFIX =>
              detectedGameEnd = true
              onIWon()
            case HELLO_PREFIX =>
              onHello(pushSocket)
              isConnected = Some()
            case PROGRESS_PREFIX =>
              val attrs = data(1).split(";")
              val progress = attrs(0).toInt
              onProgress(progress)
            case ALIVE_PREFIX =>
              println("enemy alive")
              onAlive()
            case _ => println(s"UPS, wrong prefix $rawData")
          }
        }

        if (!buffer.isEmpty && pollItems(1).isWritable) {
          val message = buffer.poll()
          pushSocket.send(message)
          if (message.startsWith(IWON_PREFIX) || message.startsWith(ILOOSE_PREFIX)) {
            detectedGameEnd = true
            shouldStop = true
          }
        }
      } catch {
        case e: InterruptedException =>
          println("Interrupted network thread")
          shouldStop = true
      }
    }

    if (!detectedGameEnd) {
      pushSocket.send(s"$IWON_PREFIX:")
    }

    buffer.clear()
    bufferIn.clear()
    pullSocket.close()
    pushSocket.close()
    isConnected = None
    println("Connection closed")
  }
}

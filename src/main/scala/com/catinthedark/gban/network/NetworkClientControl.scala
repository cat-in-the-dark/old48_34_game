package com.catinthedark.gban.network

import com.catinthedark.gban.common.Const
import org.zeromq.ZMQ.{PollItem, Poller}
import org.zeromq.{ZContext, ZMQ}

class NetworkClientControl(serverAddress: String) extends NetworkControl {
  override def run(): Unit = {
    println(s"Start connecting to $serverAddress")
    val ctx = new ZContext()
    val pullSocket = ctx.createSocket(ZMQ.PULL)
    val pushSocket = ctx.createSocket(ZMQ.PUSH)
    pullSocket.connect(s"tcp://$serverAddress:${Const.serverPushPort}")
    pushSocket.connect(s"tcp://$serverAddress:${Const.serverPullPort}")
    pushSocket.send(s"$HELLO_PREFIX:")

    val pollItems = Array(new PollItem(pullSocket, Poller.POLLIN), new PollItem(pushSocket, Poller.POLLOUT))
    var shouldStop: Boolean = false

    var detectedGameEnd: Boolean = false

    while (!shouldStop && !Thread.currentThread().isInterrupted) {
      try {
        ZMQ.poll(pollItems, Const.pollTimeout)
        if (pollItems(0).isReadable) {
          val rawData = pullSocket.recvStr()
          println(s"Received data from server $rawData")
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
              println(s"Connected to $serverAddress")
              isConnected = Some()
            case PROGRESS_PREFIX =>
              val attrs = data(1).split(";")
              val progress = attrs(0).toInt
              onProgress(progress)
            case _ => println(s"UPS, wrong prefix $rawData")
          }
        }

        if (!buffer.isEmpty && pollItems(1).isWritable) {
          val message = buffer.poll()
          pushSocket.send(message)
          if (message.startsWith(IWON_PREFIX) || message.startsWith(ILOOSE_PREFIX)) {
            shouldStop = true
            detectedGameEnd = true
          }
        }
      } catch {
        case e: InterruptedException =>
          shouldStop = true
          println("client interrupted")
      }
    }

    if (!detectedGameEnd) {
      pushSocket.send(s"$IWON_PREFIX:")
    }

    buffer.clear()
    pullSocket.close()
    pushSocket.close()
    isConnected = None
    println("Client connection closed")
  }

  override def isServer: Boolean = false
}

package com.catinthedark.gban.network

import java.util.concurrent.ConcurrentLinkedQueue

import com.catinthedark.lib.Pipe

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
}

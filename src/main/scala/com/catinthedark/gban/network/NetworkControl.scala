package com.catinthedark.gban.network

import java.util.concurrent.ConcurrentLinkedQueue

trait NetworkControl extends Runnable {
  var isConnected: Option[Unit] = None  
  
  val MOVE_PREFIX = "MOVE"
  val SHOOT_PREFIX = "SHOOT"
  val ILOOSE_PREFIX = "ILOOSE"
  val IWON_PREFIX = "IWON"
  val HELLO_PREFIX = "HELLO"
  
  val buffer: ConcurrentLinkedQueue[String] = new ConcurrentLinkedQueue[String]()
  
  var moveListener: (Float, Boolean) => Unit = { (x, standUp) =>
    println(s"NO_MOVE_LISTENER: $x $standUp")
  }

  var shootListener: (Unit) => Unit = { _ =>
    println("NO_SHOOT_LISTENER")
  }

  var iLooseListener: (Unit) => Unit = { _ =>
    println("NO_I_LOOSE_LISTENER")
  }

  var iWonListener: (Unit) => Unit = { _ =>
    println("NO_I_WON_LISTENER")
  }

  def onMove(f: (Float, Boolean) => Unit): Unit = {
    this.moveListener = f
  }
  
  def onShoot(f: Unit => Unit): Unit = {
    this.shootListener = f
  }
  
  def onILoose(f: Unit => Unit): Unit = {
    this.iLooseListener = f
  }
  
  def onIWon(f: Unit => Unit): Unit = {
    this.iWonListener = f
  }
  
  def move(x: Float, standUp: Boolean): Unit = {
    buffer.add(s"$MOVE_PREFIX:$x;$standUp")
  }
  
  def shoot(): Unit = {
    buffer.add(s"$SHOOT_PREFIX:")
  }
  
  def iLoose(): Unit = {
    buffer.add(s"$ILOOSE_PREFIX:")
  }
  
  def iWon(): Unit = {
    buffer.add(s"$IWON_PREFIX:")
  }
}

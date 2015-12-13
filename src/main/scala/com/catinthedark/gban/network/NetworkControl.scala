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
  
  val buffer: ConcurrentLinkedQueue[String] = new ConcurrentLinkedQueue[String]()

  val onMove = new Pipe[(Float, Boolean)]()
  val onShoot = new Pipe[Boolean]()
  val onILoose = new Pipe[Unit]()
  val onIWon = new Pipe[Unit]()
  
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

  def isServer: Boolean
}

package com.catinthedark.gban

import com.catinthedark.gban.units._
import com.catinthedark.lib.{Interval, LocalDeferred, YieldUnit}

/**
  * Created by over on 18.04.15.
  */
class GameState(shared0: Shared0) extends YieldUnit[Boolean] {
  val shared1 = new Shared1(shared0)
  val view = new View(shared1)
  val enemyView = new EnemyView(shared1)
  val control = new Control(shared1) with LocalDeferred
  val waterControl = new WaterControl(shared1) with Interval {
    val interval = 0.2f
  }
  var forceReload = false
  var iLoose = false
  var iWon = false

  control.onPlayerStateChanged.ports += view.onPlayerStateChanged
  control.onMoveLeft.ports += view.onMoveLeft
  control.onMoveRight.ports += view.onMoveRight
  control.onShoot.ports += view.onShoot
  control.onGameReload + (_ => {
    forceReload = true
    stopNetworkThread()
  })

  def onILoose(u: Unit) = {
    iLoose = true
    stopNetworkThread()
  }

  def onIWon(u: Unit) = {
    iWon = true
    stopNetworkThread()
  }

  def stopNetworkThread(): Unit = {
    println("Trying to stop network thread")
    if (shared0.networkControlThread != null) {
      shared0.networkControlThread.interrupt()
    }
  }
  
  shared0.networkControl.onMove.ports += enemyView.onMove
  shared0.networkControl.onShoot.ports += enemyView.onShoot

  shared0.networkControl.onILoose.ports += onILoose
  shared0.networkControl.onIWon.ports += onIWon

  val children = Seq(view, enemyView, control, waterControl)

  override def onActivate(): Unit = {
    children.foreach(_.onActivate())
  }

  override def onExit(): Unit = {
    children.foreach(_.onExit())
  }

  override def run(delta: Float): Option[Boolean] = {
    children.foreach(_.run(delta))

    if (forceReload) {
      forceReload = false
      Some(false)
    } else if (iLoose) {
      iLoose = false
      Some(false)
    } else if (iWon) {
      iWon = false
      Some(true)
    } else {
      None
    }
  }
}

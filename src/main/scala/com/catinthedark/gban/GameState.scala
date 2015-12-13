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

  control.onPlayerStateChanged.ports += view.onPlayerStateChanged
  control.onMoveLeft.ports += view.onMoveLeft
  control.onMoveRight.ports += view.onMoveRight
  control.onGameReload + (_ => forceReload = true)
  
  shared0.networkControl.onMove.ports += enemyView.onMove
  shared0.networkControl.onShoot.ports += enemyView.onShoot

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
    } else {
      None
    }
  }
}

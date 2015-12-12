package com.catinthedark.gban

import com.catinthedark.gban.units.{Control, Shared1, View}
import com.catinthedark.lib.{LocalDeferred, YieldUnit}

/**
  * Created by over on 18.04.15.
  */
class GameState(shared0: Shared0) extends YieldUnit[Boolean] {
  val shared1 = new Shared1(shared0)
  val view = new View(shared1)
  val control = new Control(shared1) with LocalDeferred

  control.onSitStand.ports += (view.onSitStand)

  val children = Seq(view, control)

  override def onActivate(): Unit = {
    children.foreach(_.onActivate())
  }

  override def onExit(): Unit = {
    children.foreach(_.onExit())
  }

  override def run(delta: Float): Option[Boolean] = {
    children.foreach(_.run(delta))

    None
  }
}

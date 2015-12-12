package com.catinthedark.gban

import com.catinthedark.lib.YieldUnit

/**
  * Created by over on 18.04.15.
  */
class GameState(shared: Shared0) extends YieldUnit[Boolean] {
  override def onActivate(): Unit = {

  }

  override def onExit(): Unit = {

  }

  override def run(delta: Float): Option[Boolean] = {
    println("game!")
    None
  }
}

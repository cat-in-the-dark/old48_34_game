package com.catinthedark.gban

import com.catinthedark.lib.YieldUnit

/**
  * Created by over on 12.12.15.
  */
class PairingState(shared0: Shared0) extends YieldUnit[Boolean] {
  override def onActivate(): Unit = {

  }

  override def onExit(): Unit = {

  }

  override def run(delta: Float): Option[Boolean] = {
    Some(true)
  }
}

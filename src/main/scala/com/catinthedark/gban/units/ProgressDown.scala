package com.catinthedark.gban.units

import com.catinthedark.gban.common.Const
import com.catinthedark.lib.SimpleUnit

class ProgressDown(shared1: Shared1) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    shared1.player.progress = Math.max(0, shared1.player.progress - Const.Balance.progressDownSpeed())
    shared1.shared0.networkControl.progress(shared1.player.progress)
  }

  def onEnemyProgress(progress: Int): Unit = {
    shared1.enemy.progress = progress
  }
}

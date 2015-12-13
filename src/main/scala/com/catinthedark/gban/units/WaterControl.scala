package com.catinthedark.gban.units

import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.DOWN
import com.catinthedark.lib.SimpleUnit

class WaterControl(val shared: Shared1) extends SimpleUnit {
  override def run(delta: Float): Unit = {
    val player = shared.player
    if (player.state == DOWN && player.x < Const.UI.playerMinX() + Const.UI.pumpEpsilon())
      player.water = Math.min(player.water + Const.Balance.waterSpeed(), Const.Balance.bucketVolume)

  }
}

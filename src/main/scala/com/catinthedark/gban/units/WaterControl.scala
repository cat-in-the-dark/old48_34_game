package com.catinthedark.gban.units

import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.DOWN
import com.catinthedark.lib.SimpleUnit

class WaterControl(val shared: Shared1) extends SimpleUnit {
  var was = false

  def doAction(now: Boolean) = {
    (was, now) match {
      case (false, true) => Assets.Audios.waterIn.play()
      case (true, false) => Assets.Audios.waterIn.stop()
      case _ =>
    }
    was = now
  }

  override def run(delta: Float): Unit = {
    val player = shared.player
    if (player.water == Const.Balance.bucketVolume)
      return doAction(false)

    if (player.state == DOWN && player.x < Const.UI.playerMinX() + Const.UI.pumpEpsilon()) {
      player.water = Math.min(player.water + Const.Balance.waterSpeed(), Const.Balance.bucketVolume)
      return doAction(true)
    }

    return doAction(false)
  }
}

package com.catinthedark.gban.units

import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.DOWN
import com.catinthedark.lib.SimpleUnit

class WaterControl(val shared: Shared1) extends SimpleUnit {
  var was = false

  def doInAction(now: Boolean) = {
    (was, now) match {
      case (false, true) => Assets.Audios.waterIn.play()
      case (true, false) => Assets.Audios.waterIn.stop()
      case _ =>
    }
    was = now
  }

  def doOutAction() = {

  }

  override def run(delta: Float): Unit = {
    val player = shared.player

    if (player.water != 0 &&
      player.state == DOWN &&
      player.x + Const.UI.playerDownWH().x >= (Const.UI.plantPos().x - Const.UI.pumpEpsilon())) {

      player.water -= Math.min(Const.Balance.waterOutSpeed(), player.water)
      player.progress = Math.min(player.progress + Const.Balance.waterOutSpeed(), Const.Balance.maxProgress)
      doOutAction()
    }

    if (player.water != Const.Balance.bucketVolume &&
      player.state == DOWN &&
      player.x < Const.UI.playerMinX() + Const.UI.pumpEpsilon()) {

      player.water = Math.min(player.water + Const.Balance.waterSpeed(), Const.Balance.bucketVolume)
      return doInAction(true)
    } else {
      doInAction(false)
    }
  }
}

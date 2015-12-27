package com.catinthedark.gban.units

import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.{CRAWLING, DOWN}
import com.catinthedark.lib.SimpleUnit

class WaterControl(val shared: Shared1) extends SimpleUnit {
  var wasIn = false

  def doInAction(now: Boolean) = {
    (wasIn, now) match {
      case (false, true) => Assets.Audios.waterIn.play()
      case (true, false) => Assets.Audios.waterIn.stop()
      case _ =>
    }
    wasIn = now
  }

  var wasOut = false

  def doOutAction(now: Boolean) = {
    (wasOut, now) match {
      case (false, true) => Assets.Audios.waterOut.play()
      case (true, false) => Assets.Audios.waterOut.stop()
      case _ =>
    }
    wasOut = now
  }

  override def run(delta: Float): Unit = {
    val player = shared.player

    if (player.water != 0 &&
      (player.state == DOWN || player.state == CRAWLING) &&
      player.x + Const.UI.playerDownWH().x >= (Const.UI.plantPos().x - Const.UI.pumpEpsilon())) {

      player.water -= Math.min(Const.Balance.waterOutSpeed(), player.water)
      player.progress = Math.min(player.progress + Const.Balance.waterOutSpeed(), Const.Balance.maxProgress)
      val newLevel = Math.ceil(player.progress.toFloat / Const.Balance.maxProgress * Const.Balance.progressLevels).toInt - 1

      player.progressLevel = Math.max(player.progressLevel, newLevel)

      doOutAction(true)
    }
    else
      doOutAction(false)

    if (player.water != Const.Balance.bucketVolume &&
      (player.state == DOWN || player.state == CRAWLING) &&
      player.x < Const.UI.playerMinX() + Const.UI.pumpEpsilon()) {

      player.water = Math.min(player.water + Const.Balance.waterSpeed(), Const.Balance.bucketVolume)
      doInAction(true)
    } else {
      doInAction(false)
    }
  }

  override def onExit(): Unit = {
    Assets.Audios.waterIn.stop()
    Assets.Audios.waterOut.stop()
  }
}

package com.catinthedark.gban

import com.catinthedark.gban.common.Const
import com.catinthedark.gban.units._
import com.catinthedark.lib.{Interval, LocalDeferred, YieldUnit}

/**
  * Created by over on 18.04.15.
  */
class GameState(shared0: Shared0) extends YieldUnit[Boolean] {
  val shared1 = new Shared1(shared0)
  val view = new View(shared1)
  val control = new Control(shared1) with LocalDeferred
  val waterControl = new WaterControl(shared1) with Interval {
    val interval = 0.2f
  }
  val progressDown = new ProgressDown(shared1) with Interval {
    val interval = 0.5f
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

  shared0.networkControl.onILoose.ports += onILoose
  shared0.networkControl.onIWon.ports += onIWon

  shared0.networkControl.onProgress.ports += progressDown.onEnemyProgress
  val children = Seq(view, control, waterControl, progressDown)


  override def onActivate(): Unit = {
    Assets.Audios.bgm.play()
    children.foreach(_.onActivate())
  }

  override def onExit(): Unit = {
    Assets.Audios.bgm.stop()
    children.foreach(_.onExit())
    shared1.reset()
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
    } else if(shared1.player.progress >= Const.Balance.maxProgress){
      shared0.networkControl.iLoose()
      Some(true)
    }else if(shared1.player.progress <= 0){
      shared0.networkControl.iWon()
      Some(false)
    } else {
      None
    }
  }
}

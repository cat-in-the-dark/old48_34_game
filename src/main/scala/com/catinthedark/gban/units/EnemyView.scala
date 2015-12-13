package com.catinthedark.gban.units

import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.{KILLED, DOWN, SHOOTING, UP}
import com.catinthedark.lib.{SimpleUnit, Deferred, MagicSpriteBatch}

abstract class EnemyView(val shared: Shared1) extends SimpleUnit with Deferred {
  def onShoot(amIDie: Boolean): Unit = {
    println(s"I receive shoot $amIDie")
    if (amIDie) {
      shared.enemy.frags += 1
      shared.player.state = KILLED
      defer(Const.Balance.restoreCooldown, () => {
        println("I Alive again")
        shared.player.state = UP
        shared.shared0.networkControl.move(shared.player.x, standUp = true)
      })
    }
  }

  def onMove(pos: (Float, Boolean)): Unit = {
    println(s"Enemy moved $pos")
    shared.enemy.x = pos._1
    shared.enemy.state = if (pos._2) {
      UP
    } else {
      DOWN
    }
  }

  def render(delta: Float, magicBatch: MagicSpriteBatch) = {
    shared.enemy.state match {
      case UP | SHOOTING =>
        //val rect
        magicBatch.drawWithDebug(
          shared.enemy.texture(delta),
          shared.enemy.rect, shared.enemy.physRect)
        
      case DOWN => //don't draw enemy
      case _ => println(s"Don't draw enemy state ${shared.enemy.state}")
    }

  }
}

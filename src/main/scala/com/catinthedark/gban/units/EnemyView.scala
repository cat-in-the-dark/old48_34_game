package com.catinthedark.gban.units

import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.{DOWN, UP, SHOOTING}
import com.catinthedark.lib.{MagicSpriteBatch, SimpleUnit}

class EnemyView(val shared: Shared1) extends SimpleUnit {
  val magicBatch = new MagicSpriteBatch(Const.debugEnabled())
  
  def onShoot(amIDie: Boolean): Unit = {
    println(s"I receive shoot $amIDie")
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

  override def run(delta: Float) = {
    shared.enemy.state match {
      case UP | SHOOTING =>
        magicBatch managed { batch =>
          magicBatch.drawWithDebug(
            shared.enemy.texture(delta),
            shared.enemy.rect, shared.enemy.rect)
        }
      case DOWN => //don't draw enemy
      case _ => println(s"Don't draw enemy state ${shared.enemy.state}")
    }
    
  }
}

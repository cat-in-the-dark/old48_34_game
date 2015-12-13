package com.catinthedark.gban.units

import com.catinthedark.gban.view._
import com.catinthedark.lib.MagicSpriteBatch
import com.catinthedark.lib.constants.{FRange, Vec2Range}

class EnemyView(val shared: Shared1, range: Vec2Range, speed: FRange) {
  var y = range().y
  var dir: Option[State] = None

  def go(state: State) = {
    dir = Some(state)
  }

  def onShoot(amIDie: Boolean): Unit = {
    println(s"I receive shoot $amIDie")
    if (amIDie) {
      shared.enemy.frags += 1
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
        val rect = shared.enemy.rect
        val prect = shared.enemy.physRect

        rect.y = y
        prect.y = y
        magicBatch.drawWithDebug(
          shared.enemy.texture(delta),
          rect, prect)

      case DOWN => //don't draw enemy
      case _ => println(s"Don't draw enemy state ${shared.enemy.state}")
    }

    dir map { dir =>
      val newY = dir match {
        case UP | RUNNING | SHOOTING =>  y + delta * speed()
        case DOWN | CRAWLING => y - delta * speed()
        case _ => y
      }

      val vecRange = range()

      if (newY < vecRange.x)
        y = vecRange.x
      else if (newY > vecRange.y)
        y = vecRange.y
      else
        y = newY
    }
  }
}

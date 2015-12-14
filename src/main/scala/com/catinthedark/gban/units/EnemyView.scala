package com.catinthedark.gban.units

import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view._
import com.catinthedark.lib.{SimpleUnit, Deferred, MagicSpriteBatch}
import com.catinthedark.lib.constants.{FRange, Vec2Range}

abstract class EnemyView(val shared: Shared1, range: Vec2Range, speed: FRange) extends SimpleUnit with Deferred {
  var y = range().y
  var dir: Option[State] = None

  def go(state: State) = {
    dir = Some(state)
  }


  def onShoot(amIDie: Boolean): Unit = {
    println(s"I receive shoot $amIDie")
    shared.enemy.state = SHOOTING
    if (amIDie) {
      shared.enemy.frags += 1
      shared.player.state = KILLED
      shared.player.water /= 2
      defer(Const.Balance.restoreCooldown, () => {
        println("I Alive again")
        shared.player.state = UP
        shared.shared0.networkControl.move(shared.player.x, standUp = true)
        shared.shared0.networkControl.iAlive()
      })
      Assets.Audios.shoot.play(1)
    } else {
      Assets.Audios.ricochet.play()
    }
  }

  def onMove(pos: (Float, Boolean)): Unit = {
    if(shared.enemy.state == KILLED){
      println("discard move due to enemy is dead! wait for alive!")
      return
    }


    println(s"Enemy moved $pos")
    shared.enemy.x = pos._1
    shared.enemy.state = if (pos._2) {
      UP
    } else {
      DOWN
    }
  }

  def onAlive(u: Unit) = {
    if(shared.enemy.state == KILLED)
      shared.enemy.state = UP
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
    if (shared.enemy.state != SHOOTING) {
      shared.enemy.animationCounter = 0
    }

    dir map { dir =>
      val newY = dir match {
        case UP | RUNNING | SHOOTING => y + delta * speed()
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

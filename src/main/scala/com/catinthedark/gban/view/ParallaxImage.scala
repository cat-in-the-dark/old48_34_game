package com.catinthedark.gban.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.lib.constants.Vec2Range

sealed trait State
case object UP extends State
case object DOWN extends State
case object RUNNING extends State
case object CRAWLING extends State
case object SHOOTING extends State

class ParallaxImage(val tex: Texture, range: Vec2Range, initial: State, inc: Boolean = true) {

  var direction: Option[State] = None
  var y = initial match {
    case UP => range().x
    case DOWN => range().y
  }

  def go(d: State) = direction = Some(d)

  def render(delta: Float, batch: SpriteBatch, xPos: Float, animationSpeed: Float) = {
    batch.draw(tex, xPos, y)

    direction map { dir: State =>
      val newY = dir match {
        case UP | RUNNING | SHOOTING => if (inc) y - delta * animationSpeed else y + delta * animationSpeed
        case DOWN | CRAWLING => if (inc) y + delta * animationSpeed else y - delta * animationSpeed
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

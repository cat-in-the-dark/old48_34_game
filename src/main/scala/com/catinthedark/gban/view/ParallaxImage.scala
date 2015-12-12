package com.catinthedark.gban.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.lib.constants.Vec2Range

sealed trait UpDown
case object UP extends UpDown
case object DOWN extends UpDown

class ParallaxImage(val tex: Texture, range: Vec2Range, initial: UpDown, inc: Boolean = true) {

  var direction: Option[UpDown] = None
  var y = initial match {
    case UP => range().x
    case DOWN => range().y
  }

  def go(d: UpDown) = direction = Some(d)

  def render(delta: Float, batch: SpriteBatch, xPos: Float, animationSpeed: Float) = {
    batch.draw(tex, xPos, y)

    direction map { dir: UpDown =>
      val newY = dir match {
        case UP => if (inc) y - delta * animationSpeed else y + delta * animationSpeed
        case DOWN => if (inc) y + delta * animationSpeed else y - delta * animationSpeed
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

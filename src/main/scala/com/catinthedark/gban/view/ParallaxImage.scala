package com.catinthedark.gban.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.lib.constants.Vec2Range


class ParallaxImage(val tex: Texture, range: Vec2Range, initial: State, inc: Boolean = true) {

  var direction: Option[State] = None
  var y = initial match {
    case UP => range().x
    case DOWN => range().y
    case _ => range().x
  }

  def go(d: State) = direction = Some(d)

  def render(delta: Float, batch: SpriteBatch, xPos: Float, animationSpeed: Float) = {
    batch.draw(tex, xPos, y)

    direction foreach { dir: State =>
      val newY = dir match {
        case UP | RUNNING | SHOOTING => if (inc) y - delta * animationSpeed else y + delta * animationSpeed
        case DOWN | CRAWLING => if (inc) y + delta * animationSpeed else y - delta * animationSpeed
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

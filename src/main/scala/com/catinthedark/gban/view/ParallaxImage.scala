package com.catinthedark.gban.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.catinthedark.lib.constants.{Vec2Range, FRange}

sealed trait Direction
case object UP extends Direction
case object DOWN extends Direction

class ParallaxImage(val tex: Texture, range: Vec2Range, initial: Direction) {

  var direction: Option[Direction] = None
  var y = initial match {
    case UP => range().x
    case DOWN => range().y
  }

  def go(d: Direction) = direction = Some(d)

  def render(delta: Float, batch: SpriteBatch, xPos: Float, animationSpeed: Float) = {
    batch.draw(tex, xPos, y)

    direction map { dir: Direction =>
      val newY = dir match {
        case UP => y + delta * animationSpeed
        case DOWN => y - delta * animationSpeed
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

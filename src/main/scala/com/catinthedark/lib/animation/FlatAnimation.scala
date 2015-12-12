package com.catinthedark.lib.animation

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.catinthedark.lib.Magic.richifySpriteBatch

/**
 * Created by over on 15.03.15.
 */
class FlatAnimation(val tex: Texture, val startPos: Vector2,
                    val interpolator: (Vector2, Vector2, Float, Float) => Vector2) extends Animation {
  var currentPos = startPos
  var arg = 0f

  override def render(delta: Float, batch: SpriteBatch): Unit = {
    batch.drawCentered(tex, currentPos.x, currentPos.y)
    currentPos = interpolator(startPos, currentPos, arg, delta)
    arg += delta
  }
  override def reset(): Unit = {
    currentPos = startPos
    arg = 0f
  }
}

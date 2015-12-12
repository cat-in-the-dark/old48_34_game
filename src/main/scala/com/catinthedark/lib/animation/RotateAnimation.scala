package com.catinthedark.lib.animation

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

/**
 * Created by over on 15.03.15.
 */
class RotateAnimation(val tex: Texture, val pos: Vector2,
                      val interpolator: (Float, Float) => Float,
                      val angleStart: Float = 0) extends Animation {

  var angle = angleStart

  override def render(delta: Float, batch: SpriteBatch): Unit = {
    batch.draw(tex, pos.x, pos.y,
      tex.getWidth / 2, tex.getHeight / 2,
      tex.getWidth, tex.getHeight,
      1, 1,
      angle,
      0, 0,
      tex.getWidth, tex.getHeight, false, false)
  }

  override def step(delta: Float) =
    angle = interpolator(angle, delta)


  override def reset(): Unit =
    angle = angleStart
}
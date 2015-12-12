package com.catinthedark.lib

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

/**
 * Created by over on 13.12.14.
 */
object Magic {

  class RichSpriteBatch(val batch: SpriteBatch) {
    def managed(f: SpriteBatch => Unit): Unit = {
      batch.begin()
      f(batch)
      batch.end()
    }

    def drawCentered(tex: Texture, x: Float, y: Float,
                     centerX: Boolean = true, centerY: Boolean = true) =
      batch.draw(tex,
        if (centerX) x - tex.getWidth / 2 else x,
        if (centerY) y - tex.getHeight / 2 else y
      )
  }

  implicit def richifySpriteBatch(batch: SpriteBatch) = new RichSpriteBatch(batch)

  implicit def vector2ToTuple2(vec: Vector2): Tuple2[Float, Float] = (vec.x, vec.y)
  implicit def tuple2ToVector2(vec: Tuple2[Float, Float]): Vector2 = new Vector2(vec._1, vec._2)
}

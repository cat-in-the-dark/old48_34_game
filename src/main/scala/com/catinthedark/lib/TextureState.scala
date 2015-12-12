package com.catinthedark.lib

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.catinthedark.lib.Magic._

/**
 * Created by over on 13.12.14.
 */
trait TextureState extends Stub {
  val batch = new SpriteBatch
  val texture: Texture

  override def run(delta: Float): Option[Unit] = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    batch.managed { self: SpriteBatch =>
      self.draw(texture, 0, 0)
    }
    super.run(delta)
  }
}

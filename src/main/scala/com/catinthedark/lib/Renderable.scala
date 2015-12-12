package com.catinthedark.lib

import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Created by over on 03.01.15.
 */
trait Renderable {
  def render(delta: Float, batch: SpriteBatch)
}

package com.catinthedark.lib.animation

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.lib.Renderable

/**
 * Created by over on 15.03.15.
 */
trait Animation extends Renderable {
  protected def step(delta: Float) = {}

  def reset() = {}

  def animate(delta: Float, batch: SpriteBatch) = {
    step(delta)
    render(delta, batch)
  }
}

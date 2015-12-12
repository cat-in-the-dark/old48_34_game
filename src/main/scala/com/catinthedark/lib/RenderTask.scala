package com.catinthedark.lib

import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Created by over on 02.01.15.
 */
abstract class RenderTask(val time: Float, onDone: => Unit) {
  protected var stateTime = 0f

  def render(delta: Float, batch: SpriteBatch) =
    if(stateTime > time)
      throw new RuntimeException("Could not render already completed task")
    else {
      stateTime += delta
      renderFn(delta, batch)
      if(stateTime >= time)
        onDone
    }

  def renderFn(delta: Float, batch: SpriteBatch)
}

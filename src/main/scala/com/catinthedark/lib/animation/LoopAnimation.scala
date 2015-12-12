package com.catinthedark.lib.animation

/**
 * Created by over on 15.03.15.
 */
trait LoopAnimation extends TimingAnimation {
  override def step(delta: Float) = {
    super.step(delta)
    if (rendered >= time)
      reset()
  }
}
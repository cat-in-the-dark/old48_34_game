package com.catinthedark.lib.animation

/**
 * Created by over on 15.03.15.
 */
trait TimingAnimation extends Animation {
  val time: Float
  var rendered = 0f

  override def step(delta: Float): Unit = {
    if (rendered < time) {
      rendered += delta
      super.step(delta)
    }
  }

  override def reset(): Unit = {
    rendered = 0f
    super.reset()
  }

}

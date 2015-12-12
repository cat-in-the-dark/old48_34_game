package com.catinthedark.lib.animation

/**
 * Created by over on 15.03.15.
 */
trait TriggeredAnimation extends Animation {
  var started = false
  override def step(delta: Float): Unit = {
    if (started)
      super.step(delta)
  }

  def start(): Unit = started = true
}

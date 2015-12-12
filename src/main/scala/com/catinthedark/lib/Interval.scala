package com.catinthedark.lib

/**
 * Created by over on 14.03.15.
 */
trait Interval extends SimpleUnit {
  val interval: Float
  var last = 0f

  override def run(delta: Float) = {
    if (last < interval)
      last += delta
    else {
      super.run(delta)
      last = 0
    }
  }
}

package com.catinthedark.lib

/**
 * Created by over on 15.03.15.
 */
trait Deferred {
  def defer(delay: Float, f: () => Unit): Unit
  def defer(f: () => Unit): Unit = defer(0, f)
}

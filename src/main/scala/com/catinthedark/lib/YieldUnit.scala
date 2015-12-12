package com.catinthedark.lib

/**
 * Created by over on 13.12.14.
 */
trait YieldUnit[+T] {
  def onActivate()
  def run(delta: Float): Option[T]
  def onExit()
}

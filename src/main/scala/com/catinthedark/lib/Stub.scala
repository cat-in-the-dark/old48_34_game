package com.catinthedark.lib

/**
 * Created by over on 13.12.14.
 */
abstract class Stub(name: String) extends YieldUnit[Unit] {

  override def toString = name

  override def onActivate() = {}

  override def onExit() = {}

  override def run(delay: Float): Option[Unit] = None
}

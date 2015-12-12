package com.catinthedark.gban.units


import com.badlogic.gdx.{Input, InputAdapter, Gdx}
import com.catinthedark.gban.view.{DOWN, UP, Direction}
import com.catinthedark.lib._

/**
  * Created by over on 22.01.15.
  */
abstract class Control(shared: Shared1) extends SimpleUnit with Deferred with Interval {
  val interval = 30f
  val onSitStand = new Pipe[Direction]()

  override def onActivate() = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        if (keycode == Input.Keys.CONTROL_LEFT) {
          onSitStand(UP)
          shared.shared0.networkControl.move(0, standUp = false)
        }
        true
      }

      override def keyUp(keycode: Int): Boolean = {
        if (keycode == Input.Keys.CONTROL_LEFT) {
          onSitStand(DOWN)
          shared.shared0.networkControl.move(0, standUp = true)
        }
        true
      }
    })
  }

  override def run(delta: Float): Unit = super.run(delta)

  override def onExit(): Unit = Gdx.input.setInputProcessor(null)
}

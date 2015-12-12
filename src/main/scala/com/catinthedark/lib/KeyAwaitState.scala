package com.catinthedark.lib

import com.badlogic.gdx.{InputAdapter, Gdx}

/**
 * Created by over on 13.12.14.
 */
trait KeyAwaitState extends Stub {
  val keycode: Int
  var done = false

  override def onActivate(): Unit = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(kc: Int) = {
        if (keycode == kc) {
          done = true
          true
        } else false
      }
    })
  }

  override def run(delay: Float): Option[Unit] =
    if (done) Some()
    else super.run(delay)

  override def onExit(): Unit = {
    Gdx.input.setInputProcessor(null)
    done = false
  }
}

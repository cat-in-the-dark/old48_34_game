package com.catinthedark.lib

import com.badlogic.gdx.{InputAdapter, Gdx}

/**
 * Created by over on 13.12.14.
 */
trait InputHelper {
  def onKeyDown(handler: Int => Any): Unit = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int) = {
        handler(keycode)
        true
      }
    })
  }
  def unregister() = Gdx.input.setInputProcessor(null)
}

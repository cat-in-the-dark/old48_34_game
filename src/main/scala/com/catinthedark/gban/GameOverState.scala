package com.catinthedark.gban

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.catinthedark.lib.{KeyAwaitState, Stub, TextureState}

class GameOverState(val shared: Shared0) extends Stub("GameOver") with TextureState with KeyAwaitState {
  override val keycode: Int = Input.Keys.ENTER
  override val texture: Texture = Assets.Textures.gameOver

  override def onActivate(): Unit = {
    super.onActivate()
  }
}

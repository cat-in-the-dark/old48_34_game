package com.catinthedark.gban

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.catinthedark.lib.{KeyAwaitState, TextureState, Stub}

class GameWinScreen(val shared: Shared0) extends Stub("GameWin") with TextureState with KeyAwaitState {
  override val keycode: Int = Input.Keys.ENTER
  override val texture: Texture = if (shared.networkControl.isServer) {
    Assets.Textures.GoodThemePack.winScreen
  } else {
    Assets.Textures.UglyThemePack.winScreen
  }

  override def onActivate(): Unit = {
    super.onActivate()
  }
}
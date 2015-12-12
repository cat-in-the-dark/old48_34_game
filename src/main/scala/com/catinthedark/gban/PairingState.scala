package com.catinthedark.gban

import com.badlogic.gdx.graphics.Texture
import com.catinthedark.gban.network.{NetworkServerControl, NetworkClientControl}
import com.catinthedark.lib.{Stub, TextureState}

/**
  * Created by over on 12.12.15.
  */
class PairingState(shared0: Shared0, name: String) extends Stub(name) with TextureState {
  override def onActivate(): Unit = {
    super.onActivate()
    if (shared0.serverAddress != null) {
      shared0.networkControl = new NetworkClientControl(shared0.serverAddress)
    } else {
      shared0.networkControl = new NetworkServerControl()
    }
  }

  override def onExit(): Unit = {
    super.onExit()
  }

  override def run(delta: Float): Option[Unit] = {
    super.run(delta)
    //Some(true)
    if (shared0.networkControl != null) {
      shared0.networkControl.isConnected
    } else {
      None
    }
  }

  override val texture: Texture = Assets.Textures.paring
}

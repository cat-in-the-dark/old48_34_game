package com.catinthedark.gban

import com.badlogic.gdx.{Input, InputAdapter, Gdx}
import com.badlogic.gdx.graphics.Texture
import com.catinthedark.gban.network.{NetworkServerControl, NetworkClientControl}
import com.catinthedark.lib.{Stub, TextureState}

/**
  * Created by over on 12.12.15.
  */
class PairingState(shared0: Shared0, name: String) extends Stub(name) with TextureState {
  var hardSkip: Boolean = false
  
  override def onActivate(): Unit = {
    super.onActivate()
    
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keyCode: Int): Boolean = {
        keyCode match {
          case Input.Keys.BACKSPACE => hardSkip = true
          case _ => 
        }
        true
      }
    })
    
    if (shared0.serverAddress != null) {
      shared0.networkControl = new NetworkClientControl(shared0.serverAddress)
    } else {
      shared0.networkControl = new NetworkServerControl()
    }
    
    new Thread(shared0.networkControl).start()
    println("Network thread started")
  }

  override def onExit(): Unit = {
    super.onExit()
  }

  override def run(delta: Float): Option[Unit] = {
    super.run(delta)
    if (hardSkip) {
      hardSkip = false
      println("WARNING hard skip of network connection")
      return Some()
    }
    
    if (shared0.networkControl != null) {
      shared0.networkControl.isConnected
    } else {
      None
    }
  }

  override val texture: Texture = Assets.Textures.paring
}

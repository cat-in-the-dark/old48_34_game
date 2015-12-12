package com.catinthedark.gban.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.{Direction, UP, ParallaxImage}
import com.catinthedark.lib._
import Magic.richifySpriteBatch

/**
  * Created by over on 02.01.15.
  */
class View(val shared: Shared1) extends SimpleUnit {
  val myHedge = new ParallaxImage(Assets.Textures.myHedge, Const.UI.myHedgeYRange, UP)
  val ground = new ParallaxImage(Assets.Textures.ground, Const.UI.groundYRange, UP)
  val batch = new SpriteBatch()

  def onSitStand(d: Direction): Unit = {
    print("here")
    myHedge.go(d)
    ground.go(d)
  }


  override def onActivate() = {

  }

  override def run(delta: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    batch.managed { batch =>
      myHedge.render(delta, batch, 0, Const.UI.myHedgeParallaxSpeed())
      ground.render(delta, batch, 0, Const.UI.groundParallaxSpeed())
    }

  }

  override def onExit() = {
  }
}

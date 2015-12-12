package com.catinthedark.gban.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.{DOWN, UpDown, UP, ParallaxImage}
import com.catinthedark.lib._
import Magic.richifySpriteBatch

/**
  * Created by over on 02.01.15.
  */
class View(val shared: Shared1) extends SimpleUnit {
  val myHedge = new ParallaxImage(Assets.Textures.myHedge, Const.UI.myHedgeYRange, UP)
  val ground = new ParallaxImage(Assets.Textures.ground, Const.UI.groundYRange, UP)
  val road = new ParallaxImage(Assets.Textures.road, Const.UI.roadYRange, DOWN, inc = false)
  val enemyHedge = new ParallaxImage(Assets.Textures.enemyHedge, Const.UI.enemyHedgeYRange, DOWN, inc = false)
  val batch = new SpriteBatch()
  val magicBatch = new MagicSpriteBatch(Const.debugEnabled())

  def onSitStand(d: UpDown): Unit = {
    myHedge.go(d)
    ground.go(d)
    road.go(d)
    enemyHedge.go(d)
    shared.player.state = d
  }


  override def onActivate() = {

  }

  override def run(delta: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    batch.managed { batch =>
      enemyHedge.render(delta, batch, 0, Const.UI.enemyHedgeParallaxSpeed())
      road.render(delta, batch, 0, Const.UI.roadParallaxSpeed())
      myHedge.render(delta, batch, 0, Const.UI.myHedgeParallaxSpeed())
      ground.render(delta, batch, 0, Const.UI.groundParallaxSpeed())
    }
    magicBatch managed { batch =>
      magicBatch.drawWithDebug(
        shared.player.texture,
        shared.player.rect, shared.player.rect)
    }

  }

  override def onExit() = {
  }
}

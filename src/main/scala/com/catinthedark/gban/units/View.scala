package com.catinthedark.gban.units

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view._
import com.catinthedark.lib._
import Magic.richifySpriteBatch
import org.lwjgl.util.Point

/**
  * Created by over on 02.01.15.
  */
class View(val shared: Shared1) extends SimpleUnit {
  val (enemyBack, myHedge, enemyHedge, plant) = if (shared.shared0.networkControl.isServer) {
    (new ParallaxImage(Assets.Textures.GoodThemePack.background, Const.UI.enemyBackYRange, DOWN, inc = false),
      new ParallaxImage(Assets.Textures.GoodThemePack.hedge, Const.UI.myHedgeYRange, UP), 
      new ParallaxImage(Assets.Textures.GoodThemePack.enemyHedge, Const.UI.enemyHedgeYRange, DOWN, inc = false), 
      Assets.Textures.GoodThemePack.plant)
  } else {
    (new ParallaxImage(Assets.Textures.UglyThemePack.background, Const.UI.enemyBackYRange, DOWN, inc = false), 
      new ParallaxImage(Assets.Textures.UglyThemePack.hedge, Const.UI.myHedgeYRange, UP),
      new ParallaxImage(Assets.Textures.UglyThemePack.enemyHedge, Const.UI.enemyHedgeYRange, DOWN, inc = false),
      Assets.Textures.UglyThemePack.plant)
  }

  val ground = new ParallaxImage(Assets.Textures.ground, Const.UI.groundYRange, UP)
  val road = new ParallaxImage(Assets.Textures.road, Const.UI.roadYRange, DOWN, inc = false)
  val batch = new SpriteBatch()
  val magicBatch = new MagicSpriteBatch(Const.debugEnabled())

  val enemyView = new EnemyView(shared, Const.UI.enemyYRange, Const.UI.enemyParallaxSpeed) with LocalDeferred

  shared.shared0.networkControl.onMove.ports += enemyView.onMove
  shared.shared0.networkControl.onShoot.ports += enemyView.onShoot



  val hud = new Hud(shared)

  var enemyKilledPos: Float = 0
  var enemyHatHeight: Float = 0

  def onPlayerStateChanged(d: State): Unit = {
    enemyBack.go(d)
    myHedge.go(d)
    ground.go(d)
    road.go(d)
    enemyHedge.go(d)
    enemyView.go(d)
    shared.player.state = d
    shared.player.animationCounter = 0
    d match {
      case DOWN | CRAWLING => shared.shared0.networkControl.move(shared.player.x, standUp = false)
      case UP | RUNNING => shared.shared0.networkControl.move(shared.player.x, standUp = true)
      case _ => println(s"Handle this later or never $d")
    }
  }
  
  def onShoot(point: Point): Unit = {
    val amIExactly = if (shared.enemy.physRect != null) {
      shared.enemy.physRect.contains(point.getX, point.getY)
    } else {
      false
    }
    if (amIExactly) {
      shared.player.frags += 1
      enemyKilledPos = shared.enemy.rect.x
      enemyHatHeight = shared.enemy.rect.y
      shared.enemy.state = KILLED
    }
    println(s"I shoot $point in the ${shared.enemy.physRect} and amIExactly: $amIExactly")
    shared.shared0.networkControl.shoot(amIExactly)
  }

  def onMoveLeft(u: Unit): Unit = {
    shared.player.state match {
      case UP | RUNNING =>
        moveLeft(Const.gamerSpeed())
        shared.shared0.networkControl.move(shared.player.x, standUp = true)
      case DOWN | CRAWLING =>
        moveLeft(Const.gamerSlowSpeed())
      case _ =>
    }
  }

  def onMoveRight(u: Unit): Unit = {
    shared.player.state match {
      case UP | RUNNING =>
        moveRight(Const.gamerSpeed())
        shared.shared0.networkControl.move(shared.player.x, standUp = true)
      case DOWN | CRAWLING =>
        moveRight(Const.gamerSlowSpeed())
      case _ =>
    }
  }

  def moveLeft(speed: Float): Unit = {
//    println(shared.player.x, Const.UI.playerUpWH().x)
    if (shared.player.x - speed <= Const.UI.playerMinX()) {
      shared.player.x = Const.UI.playerMinX()
    } else {
      shared.player.x -= speed
    }
  }

  def moveRight(speed: Float): Unit = {
//    println(shared.player.x, Const.UI.playerUpWH().x)
    if (shared.player.x + speed >= Const.Projection.width - Const.UI.playerUpWH().x) {
      shared.player.x = Const.Projection.width - Const.UI.playerUpWH().x
    } else {
      shared.player.x += speed
    }
  }

  override def onActivate() = {

  }

  override def run(delta: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 0)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    
    enemyView.run(delta)

    batch.managed { batch =>
      batch.draw(Assets.Textures.sky, Const.UI.skyPos().x, Const.UI.skyPos().y)
      enemyBack.render(delta, batch, 0, Const.UI.enemyBackParallaxSpeed())
    }

    magicBatch managed { batch =>
      enemyView.render(delta, batch)
    }

    batch.managed { batch =>
      enemyHedge.render(delta, batch, 0, Const.UI.enemyHedgeParallaxSpeed())
      road.render(delta, batch, 0, Const.UI.roadParallaxSpeed())
      myHedge.render(delta, batch, 0, Const.UI.myHedgeParallaxSpeed())
      ground.render(delta, batch, 0, Const.UI.groundParallaxSpeed())
      batch.draw(Assets.Textures.waterPump, Const.UI.pumpPosition().x, Const.UI.pumpPosition().y)

      if (shared.enemy.state == KILLED) {
        enemyHatHeight += delta * Const.UI.hatSpeed
        if (shared.shared0.networkControl.isServer) {
          batch.draw(Assets.Textures.uglyHat, enemyKilledPos, enemyHatHeight)
        } else {
          batch.draw(Assets.Textures.goodHat, enemyKilledPos, enemyHatHeight)
        }
      }
    }

    magicBatch managed { batch =>
      magicBatch.drawWithDebug(
        shared.player.texture(delta),
        shared.player.rect, shared.player.rect)
    }

    batch managed { render =>
      //batch.draw(Assets.Textures.corn(0), Const.UI.plantPos().x + 20, Const.UI.plantPos().y + 10)
      batch.draw(plant(shared.player.progressLevel), Const.UI.plantPos().x, Const.UI.plantPos().y)
    }

    hud.render()
  }

  override def onExit() = {
  }
}

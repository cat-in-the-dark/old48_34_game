package com.catinthedark.gban

import com.badlogic.gdx.{utils, Gdx}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.catinthedark.gban.common.Const

/**
  * Created by over on 13.12.14.
  */
object Assets {

  object Textures {
    val logo = new Texture(Gdx.files.internal("textures/logo.png"))

    val t1 = new Texture(Gdx.files.internal("textures/menu.gif"))
    val t2 = new Texture(Gdx.files.internal("textures/tutor_1.gif"))
    val t3 = new Texture(Gdx.files.internal("textures/tutor_2.gif"))
    val t4 = new Texture(Gdx.files.internal("textures/tutor_3.gif"))
    val paring = new Texture(Gdx.files.internal("textures/pairing.png"))
    val gameOver = new Texture(Gdx.files.internal("textures/gameover.gif"))
    val gameWin = new Texture(Gdx.files.internal("textures/gamewin.gif"))

    val myHedge = new Texture(Gdx.files.internal("textures/myHedge.gif"))
    val ground = new Texture(Gdx.files.internal("textures/myGround.gif"))

    val road = new Texture(Gdx.files.internal("textures/road.gif"))
    val enemyHedge = new Texture(Gdx.files.internal("textures/enemyHedge.gif"))

    val goodFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/player_good.png")), 200, 360)
    val goodUp = goodFrames(0)(0)
    val goodDown = goodFrames(0)(1)

    val uglyFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/player_ugly.png")), 200, 360)

    val uglyUp = uglyFrames(0)(0)
    val uglyDown = uglyFrames(0)(1)

    val enemyGoodFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/enemy_good.png")), 96, 80)
    val enemyGood = enemyGoodFrames(0)(0)

    val enemyUglyFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/enemy_ugly.png")), 96, 80)
    val enemyUgly = enemyUglyFrames(0)(0)

  }

  object Fonts {
    val mainGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"))
    val moneyFontParam = new FreeTypeFontParameter()
    moneyFontParam.size = 44
    val hudFont = mainGenerator.generateFont(moneyFontParam)
    hudFont.setColor(167f / 255, 128f / 255, 183f / 255, 1)
  }

  object Animations {
    private def loopingAnimation(frames: Array[Array[TextureRegion]], frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(Const.UI.animationSpeed, array, Animation.PlayMode.LOOP)
    }

    private def normalAnimation(speed: Float, frames: Array[Array[TextureRegion]], frameIndexes: (Int, Int)*): Animation = {
      val array = new utils.Array[TextureRegion]
      frameIndexes.foreach(i => array.add(frames(i._1)(i._2)))
      new Animation(speed, array, Animation.PlayMode.NORMAL)
    }
    trait PlayerAnimationPack {
      val shooting: Animation
      val running: Animation
      val crawling: Animation
    }
    object goodAnimations extends PlayerAnimationPack{

      val shooting = normalAnimation(Const.UI.animationSpeed, Textures.goodFrames,
        (0, 0), (0, 2), (0, 0))

      val running = loopingAnimation(Textures.goodFrames,
        (0, 0), (0, 3), (0, 4))

      val crawling = loopingAnimation(Textures.goodFrames,
        (0, 1), (0, 5), (0, 6))
    }

    object uglyAnimations extends PlayerAnimationPack{

      val shooting = normalAnimation(Const.UI.animationSpeed, Textures.uglyFrames,
        (0, 0), (0, 2), (0, 0))

      val running = loopingAnimation(Textures.uglyFrames,
        (0, 0), (0, 3), (0, 4))

      val crawling = loopingAnimation(Textures.uglyFrames,
        (0, 1), (0, 5), (0, 6))
    }

    val enemyGoodShooting = normalAnimation(Const.UI.animationSpeed, Textures.enemyGoodFrames,
      (0, 0), (0, 1), (0, 0))

    val enemyUglyShooting = normalAnimation(Const.UI.animationSpeed, Textures.enemyUglyFrames,
      (0, 0), (0, 1), (0, 0))
  }

  object Audios {
    val roundEnd = Gdx.audio.newSound(Gdx.files.internal("sound/round_end.mp3"))

    val bgmCool = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm_cool.mp3"))
    bgmCool.setLooping(true)

  }

}

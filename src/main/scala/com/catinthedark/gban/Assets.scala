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

    val sky = new Texture(Gdx.files.internal("textures/sky.gif"))
    val ground = new Texture(Gdx.files.internal("textures/myGround.gif"))

    val road = new Texture(Gdx.files.internal("textures/road.gif"))
    
    trait ThemePack {
      val hedge: Texture
      val enemyHedge: Texture
      val plant: Array[Texture]
      val background: Texture
      val winScreen: Texture
      val loseScreen: Texture
      val pairing: Texture
    }
    
    object GoodThemePack extends ThemePack{
      override val hedge: Texture = new Texture(Gdx.files.internal("textures/goodHedge.gif"))
      override val plant: Array[Texture] = Array(
        new Texture(Gdx.files.internal("textures/corn1.gif")),
        new Texture(Gdx.files.internal("textures/corn2.gif")),
        new Texture(Gdx.files.internal("textures/corn3.gif")))
      override val enemyHedge: Texture = new Texture(Gdx.files.internal("textures/goodEnemyHedge.gif"))
      override val background: Texture = new Texture(Gdx.files.internal("textures/mexico.gif"))
      override val winScreen: Texture = new Texture(Gdx.files.internal("textures/gamewin.gif"))
      override val loseScreen: Texture = new Texture(Gdx.files.internal("textures/gameover.gif"))
      override val pairing: Texture = new Texture(Gdx.files.internal("textures/pairing.png"))
    }

    object UglyThemePack extends ThemePack{
      override val hedge: Texture = new Texture(Gdx.files.internal("textures/uglyHedge.gif"))
      override val plant: Array[Texture] = Array(
        new Texture(Gdx.files.internal("textures/tomatos1.gif")),
        new Texture(Gdx.files.internal("textures/tomatos2.gif")),
        new Texture(Gdx.files.internal("textures/tomatos3.gif")))
      override val enemyHedge: Texture = new Texture(Gdx.files.internal("textures/uglyEnemyHedge.gif"))
      override val background: Texture = new Texture(Gdx.files.internal("textures/america.gif"))
      override val winScreen: Texture = new Texture(Gdx.files.internal("textures/gamewin.gif"))
      override val loseScreen: Texture = new Texture(Gdx.files.internal("textures/gameover.gif"))
      override val pairing: Texture = new Texture(Gdx.files.internal("textures/pairing.png"))
    }

    val goodFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/player_good.gif")), 320, 360)

    val uglyFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/player_ugly.gif")), 320, 360)

    val uglyUp = uglyFrames(0)(0)
    val uglyDown = uglyFrames(0)(1)

    val enemyGoodFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/enemy_good.gif")), 104, 80)
    val enemyGood = enemyGoodFrames(0)(0)

    val enemyUglyFrames = TextureRegion.split(
      new Texture(Gdx.files.internal("textures/enemy_ugly.gif")), 104, 80)
    val enemyUgly = enemyUglyFrames(0)(0)

    val goodHat = new Texture(Gdx.files.internal("textures/hat_good.png"))
    val uglyHat = new Texture(Gdx.files.internal("textures/hat_ugly.png"))

    val waterPump = new Texture(Gdx.files.internal("textures/water_pump.gif"))
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
      val up: TextureRegion
      val down: TextureRegion
      val shooting: Animation
      val running: Animation
      val crawling: Animation
      val killed: TextureRegion
    }
    object goodAnimations extends PlayerAnimationPack {
      val up: TextureRegion = Textures.goodFrames(0)(0)

      val down: TextureRegion = Textures.goodFrames(0)(1)

      val shooting = normalAnimation(Const.UI.animationSpeed, Textures.goodFrames,
        (0, 0), (0, 2), (0, 0))

      val running = loopingAnimation(Textures.goodFrames,
        (0, 0), (0, 5), (0, 6))

      val crawling = loopingAnimation(Textures.goodFrames,
        (0, 1), (0, 3), (0, 4))
      
      val killed: TextureRegion = Textures.goodFrames(0)(7)
    }

    object uglyAnimations extends PlayerAnimationPack {
      val up: TextureRegion = Textures.uglyFrames(0)(0)

      val down: TextureRegion = Textures.uglyFrames(0)(1)

      val shooting = normalAnimation(Const.UI.animationSpeed, Textures.uglyFrames,
        (0, 0), (0, 2), (0, 0))

      val running = loopingAnimation(Textures.uglyFrames,
        (0, 0), (0, 5), (0, 6))

      val crawling = loopingAnimation(Textures.uglyFrames,
        (0, 1), (0, 3), (0, 4))

      val killed: TextureRegion = Textures.uglyFrames(0)(7)
    }
    
    object uglyEnemyAnimations extends PlayerAnimationPack {
      val up = Textures.enemyUgly
      val shooting = normalAnimation(Const.UI.animationSpeed, Textures.enemyUglyFrames,
        (0, 0), (0, 1), (0, 0))
      val down: TextureRegion = null
      val running: Animation = null
      val crawling: Animation = null
      val killed: TextureRegion = null
    }
    object goodEnemyAnimations extends PlayerAnimationPack {
      val up = Textures.enemyGood
      val shooting = normalAnimation(Const.UI.animationSpeed, Textures.enemyGoodFrames,
        (0, 0), (0, 1), (0, 0))
      val down: TextureRegion = null
      val running: Animation = null
      val crawling: Animation = null
      val killed: TextureRegion = null
    }
  }

  object Audios {
    val shoot = Gdx.audio.newSound(Gdx.files.internal("sound/shoot.mp3"))
    val ricochet = Gdx.audio.newSound(Gdx.files.internal("sound/ricochet.mp3"))

    val bgmCool = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm_cool.mp3"))
    bgmCool.setLooping(true)

    val waterIn = Gdx.audio.newMusic(Gdx.files.internal("sound/water_in.mp3"))
    waterIn.setLooping(false)
    val waterOut = Gdx.audio.newMusic(Gdx.files.internal("sound/voda_iz_vedra.mp3"))
    waterOut.setVolume(2)
    waterOut.setLooping(true)

  }

}

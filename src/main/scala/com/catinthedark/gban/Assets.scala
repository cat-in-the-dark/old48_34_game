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
  }

  object Fonts {
    val mainGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/main.ttf"))
    val moneyFontParam = new FreeTypeFontParameter()
    moneyFontParam.size = 44
    val moneyFrontFont = mainGenerator.generateFont(moneyFontParam)
    moneyFrontFont.setColor(167f / 255, 128f / 255, 183f / 255, 1)
  }

  object Animations {

  }

  object Audios {
    val roundEnd = Gdx.audio.newSound(Gdx.files.internal("sound/round_end.mp3"))

    val bgmCool = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm_cool.mp3"))
    bgmCool.setLooping(true)

  }

}

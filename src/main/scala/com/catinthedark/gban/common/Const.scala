package com.catinthedark.gban.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.catinthedark.lib.constants.ConstDelegate

/**
  * Created by over on 11.12.15.
  */
object Const extends ConstDelegate {
  override def delegate = Seq(
    debugEnabled,
    //    UI.myHedgeYRange, UI.myHedgeParallaxSpeed,
    //    UI.groundYRange, UI.groundParallaxSpeed,
    //    UI.roadYRange, UI.roadParallaxSpeed,
    //    UI.enemyHedgeYRange, UI.enemyHedgeParallaxSpeed)
    UI.playerY,
    UI.playerUpWH,
    UI.playerDownWH,
    gamerSpeed,
    gamerSlowSpeed
  )

  val debugEnabled = onOff("debug render", false)

  object UI {
    val skyYRange = vec2Range("sky parallax move", new Vector2(300, 600))

    val myHedgeYRange = vec2Range("myHedge parallax move", new Vector2(-31, 87))
    val myHedgeParallaxSpeed = frange("hedge parallax move", 1100f, Some(800f), Some(1500f))

    val groundYRange = vec2Range("myHedge parallax move", new Vector2(-47, 0))
    val groundParallaxSpeed = frange("hedge parallax move", 800, Some(600), Some(1500f))

    val roadYRange = vec2Range("road parallax move", new Vector2(257, 257))
    val roadParallaxSpeed = frange("road parallax move", 800, Some(500), Some(1000))

    val enemyHedgeYRange = vec2Range("enemy hedge parallax move", new Vector2(247, 290))
    val enemyHedgeParallaxSpeed = frange("enemy hedge parallax move", 500, Some(500), Some(1000))


    val playerY = frange("player y", 34, Some(0), Some(500))
    val playerUpWH = vec2Range("player up width height", new Vector2(200, 360))
    val playerDownWH = vec2Range("player down width height", new Vector2(200, 300))
  }
  
  object Projection {
    val width = 1161F
    val height = 652F
    
    def calcX(screenX: Int): Int = (screenX.toFloat * Const.Projection.width / Gdx.graphics.getWidth).toInt
    def calcY(screenY: Int): Int = (screenY.toFloat * Const.Projection.height / Gdx.graphics.getHeight).toInt
  }

  val serverPullPort = 9000
  val serverPushPort = 9001
  val pollTimeout = 10
  
  val gamerSpeed = frange("speed x", 5F, Some(0), Some(50))
  val gamerSlowSpeed = frange("slow speed x", 0.5F, Some(0), Some(20))
}

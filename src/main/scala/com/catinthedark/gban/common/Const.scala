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
//    UI.skyPos,
//    UI.enemyBackYRange,
//    UI.enemyBackParallaxSpeed,
    //    UI.myHedgeYRange, UI.myHedgeParallaxSpeed,
    //    UI.groundYRange, UI.groundParallaxSpeed,
    //    UI.roadYRange, UI.roadParallaxSpeed,
//        UI.enemyHedgeYRange, UI.enemyHedgeParallaxSpeed,
    UI.playerMinX,
//    UI.playerUpWH,
//    UI.playerDownWH,
//    gamerSpeed,
//    gamerSlowSpeed,
    //    UI.playerY,
    //    UI.playerUpWH,
    //    UI.playerDownWH,
//    HUD.myProgressPos,
//    HUD.enemyProgressPos,
//    HUD.progressWh,
    HUD.myFragsPos,
    HUD.enemyFragsPos,
//    UI.pumpPosition,
//    HUD.waterBarPos,
//    HUD.waterBarWh,
//    UI.enemyY,
//  UI.enemyYRange,
//  UI.enemyParallaxSpeed,
//    UI.plantPos
//    UI.playerY,
  UI.upbarPos,
//  UI.waterbarPos,
  HUD.ctrl1Pos,
  HUD.ctrl2Pos,
  Balance.progressDownSpeed
  )

  val debugEnabled = onOff("debug render", false)

  object UI {
    val animationSpeed = 0.2f

    val skyPos = vec2Range("sky pos", new Vector2(0, 91))

    val enemyYRange = vec2Range("enemy parallax move", new Vector2(303, 415))
    val enemyParallaxSpeed = frange("enemy parallax speed", 793, Some(300), Some(1000f))

    val enemyBackYRange = vec2Range("enemy back parallax move", new Vector2(100, 175))
    val enemyBackParallaxSpeed = frange("enemy back parallax speed", 500, Some(300), Some(1000f))

    val myHedgeYRange = vec2Range("myHedge parallax move", new Vector2(-31, 87))
    val myHedgeParallaxSpeed = frange("hedge parallax move", 1100f, Some(800f), Some(1500f))

    val groundYRange = vec2Range("myHedge parallax move", new Vector2(-47, 0))
    val groundParallaxSpeed = frange("hedge parallax move", 800, Some(600), Some(1500f))

    val roadYRange = vec2Range("road parallax move", new Vector2(257, 257))
    val roadParallaxSpeed = frange("road parallax move", 800, Some(500), Some(1000))

    val enemyHedgeYRange = vec2Range("enemy hedge parallax move", new Vector2(247, 290))
    val enemyHedgeParallaxSpeed = frange("enemy hedge parallax move", 720, Some(500), Some(1000))


    val playerY = frange("player y", 10, Some(0), Some(500))
    val playerMinX = frange("player min x", 41, Some(0), Some(500))
    val playerUpWH = vec2Range("player up width height", new Vector2(320, 360))
    val playerDownWH = vec2Range("player down width height", new Vector2(320, 300))
    
    val enemyY = frange("enemy y", 415, Some(100), Some(652))
    val enemyUpWH = vec2Range("enemy up width height", new Vector2(80, 96))
    val enemyDownWH = vec2Range("enemy down width height", new Vector2(80, 96))

    val enemyUpPhysWH = vec2Range("enemy up phys width height", new Vector2(80, 96))
    
    val pumpPosition = vec2Range("pump position", new Vector2(58, 14))
    val pumpEpsilon = frange("pump epsilon", 10, Some(0), Some(50))

    val plantPos = vec2Range("plant pos", new Vector2(900, 9))
    val plantEpsilon = frange("plant epsilon", 10, Some(0), Some(50))

    val hatSpeed = 600
    val upbarPos = vec2Range("upbar pos", new Vector2(0, 581))
    val waterbarPos = vec2Range("waterbar pos", new Vector2(14, 14))

  }

  object HUD {
    val myProgressPos = vec2Range("my progress bar position", new Vector2(78, 603))
    val enemyProgressPos = vec2Range("enemy progress bar position", new Vector2(814, 603))
    val progressWh = vec2Range("player down width height", new Vector2(252, 25))
    val myFragsPos = vec2Range("my frag pos", new Vector2(453, 630))
    val enemyFragsPos = vec2Range("enemy frag pos", new Vector2(670, 630))

    val waterBarPos = vec2Range("water bar pos", new Vector2(16, 55))
    val waterBarWh = vec2Range("water bar width height", new Vector2(24, 248))
    val ctrl1Pos = vec2Range("ctrl1 pos", new Vector2(103, 56))
    val ctrl2Pos = vec2Range("ctrl1 pos", new Vector2(897, 56))
  }


  object Projection {
    val width = 1161F
    val height = 652F
    
    val enemyOffsetX = 20F
    val enemyViewPort = width - enemyOffsetX * 2
    def projection(originX: Float): Float = originX * enemyViewPort / width
    def calcEnemyX(originX: Float): Float = enemyViewPort - projection(originX) - 90F
    
    def calcX(screenX: Int): Int = (screenX.toFloat * Const.Projection.width / Gdx.graphics.getWidth).toInt
    def calcY(screenY: Int): Int = (screenY.toFloat * Const.Projection.height / Gdx.graphics.getHeight).toInt
  }

  object Balance {
    val progressLevels = 3
    val maxProgress = 350
    val initialProgress = maxProgress / 5
    val bucketVolume = 100
    val playerCooldown: Float = 0.5f
    val weaponCooldown: Float = 1.5f
    val restoreCooldown: Float = 2f
    val waterSpeed = irange("water in speed", 5, Some(1), Some(20))
    val waterOutSpeed = irange("water out speed", 20, Some(1), Some(50))
    val progressDownSpeed = irange("progress down speed", 1, Some(0), Some(50))
  }

  val serverPullPort = 9000
  val serverPushPort = 9001
  val pollTimeout = 10
  
  val gamerSpeed = frange("speed x", 5F, Some(0), Some(50))
  val gamerSlowSpeed = frange("slow speed x", 0.5F, Some(0), Some(20))
}

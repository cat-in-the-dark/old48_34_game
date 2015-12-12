package com.catinthedark.gban.common

import com.badlogic.gdx.math.Vector2
import com.catinthedark.lib.constants.ConstDelegate

/**
  * Created by over on 11.12.15.
  */
object Const extends ConstDelegate {
  override def delegate = Seq(UI.myHedgeYRange, UI.myHedgeParallaxSpeed, UI.groundYRange, UI.groundParallaxSpeed)
  object UI {
    val skyYRange = vec2Range("sky parallax move", new Vector2(300, 600))

    val myHedgeYRange = vec2Range("myHedge parallax move", new Vector2(-31, 87))
    val myHedgeParallaxSpeed = frange("hedge parallax move", 1100f, Some(800f), Some(1500f))

    val groundYRange = vec2Range("myHedge parallax move", new Vector2(-47, 0))
    val groundParallaxSpeed = frange("hedge parallax move", 800, Some(600), Some(1500f))
  }

  val serverPullPort = 9000
  val serverPushPort = 9001
  val pollTimeout = 10
}

package com.catinthedark.gban.entity

import com.badlogic.gdx.math.Rectangle
import com.catinthedark.gban.Assets
import com.catinthedark.gban.Assets.Animations.PlayerAnimationPack
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view._

case class Enemy(var x: Float, var state: State, var frags: Int, pack: PlayerAnimationPack) {

}
case class Player(var x: Float, var state: State, var frags: Int, pack: PlayerAnimationPack, var water: Int = 0) {

  var animationCounter = 0f

  def texture (delta: Float) = {
    state match {
      case UP => Assets.Textures.goodUp
      case SHOOTING =>
        animationCounter += delta
        pack.shooting.getKeyFrame(animationCounter)
      case DOWN => Assets.Textures.goodDown
      case RUNNING =>
        animationCounter += delta
        pack.running.getKeyFrame(animationCounter)
      case CRAWLING =>
        animationCounter += delta
        pack.crawling.getKeyFrame(animationCounter)
    }
  }

  def rect: Rectangle = {
    state match {
      case UP =>
        new Rectangle(x, Const.UI.playerY(), Const.UI.playerUpWH().x, Const.UI.playerUpWH().y)
      case DOWN =>
        new Rectangle(x, Const.UI.playerY(), Const.UI.playerDownWH().x, Const.UI.playerDownWH().y)
    }
  }
}

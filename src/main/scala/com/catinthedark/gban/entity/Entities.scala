package com.catinthedark.gban.entity

import com.badlogic.gdx.math.Rectangle
import com.catinthedark.gban.Assets
import com.catinthedark.gban.Assets.Animations.PlayerAnimationPack
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view._

case class Enemy(var x: Float, var state: State, var frags: Int, pack: PlayerAnimationPack) {
  var animationCounter = 0f
  
  def texture (delta: Float) = {
    state match {
      case UP => pack.up
      case SHOOTING =>
        animationCounter += delta
        pack.shooting.getKeyFrame(animationCounter)
      case DOWN => pack.down
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
        new Rectangle(Const.Projection.calcEnemyX(x), Const.UI.enemyY(), Const.UI.enemyUpWH().x, Const.UI.enemyUpWH().y)
      case DOWN =>
        new Rectangle(Const.Projection.calcEnemyX(x), Const.UI.enemyY(), Const.UI.enemyDownWH().x, Const.UI.enemyDownWH().y)
    }
  }
}
case class Player(var x: Float, var state: State, var frags: Int, pack: PlayerAnimationPack, var water: Int = 0) {

  var animationCounter = 0f

  def texture (delta: Float) = {
    state match {
      case UP => pack.up
      case SHOOTING =>
        animationCounter += delta
        pack.shooting.getKeyFrame(animationCounter)
      case DOWN => pack.down
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
      case UP | SHOOTING | RUNNING =>
        new Rectangle(x, Const.UI.playerY(), Const.UI.playerUpWH().x, Const.UI.playerUpWH().y)
      case DOWN | CRAWLING =>
        new Rectangle(x, Const.UI.playerY(), Const.UI.playerDownWH().x, Const.UI.playerDownWH().y)
    }
  }
}

package com.catinthedark.gban.entity

import com.badlogic.gdx.math.Rectangle
import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.{DOWN, UP, UpDown}

case class Enemy(var x: Float, var state: UpDown, var frags: Int) {

}
case class Player(var x: Float, var state: UpDown, var frags: Int) {
  def texture =
    state match {
      case UP => Assets.Textures.playerUp
      case DOWN => Assets.Textures.playerDown
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

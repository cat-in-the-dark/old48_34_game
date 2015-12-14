package com.catinthedark.gban.units


import com.badlogic.gdx.{Input, InputAdapter, Gdx}
import com.catinthedark.gban.Assets

import com.catinthedark.gban.common.Const
import com.catinthedark.gban.common.Const.Balance
import com.catinthedark.gban.view._

import com.catinthedark.lib._
import org.lwjgl.util.Point

/**
  * Created by over on 22.01.15.
  */
abstract class Control(shared: Shared1) extends SimpleUnit with Deferred {
  val onPlayerStateChanged = new Pipe[State]()
  val onShoot = new Pipe[Point]()
  val onGameReload = new Pipe[Unit]()
  val onMoveLeft = new Pipe[Unit]()
  val onMoveRight = new Pipe[Unit]()

  val STAND_KEY = Input.Keys.CONTROL_LEFT

  override def onActivate() = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        keycode match {
          case Input.Keys.ESCAPE => onGameReload()
          case _ =>
        }
        true
      }

      override def keyUp(keycode: Int): Boolean = {
        keycode match {
          case Input.Keys.A | Input.Keys.D =>
            shared.player.state match {
              case CRAWLING | DOWN =>
                onPlayerStateChanged(DOWN)
              case UP | RUNNING =>
                onPlayerStateChanged(UP)
              case _ =>
            }
          case _ =>
        }
        true
      }

      override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
        if (pointer == Input.Buttons.LEFT) {
          val x = Const.Projection.calcX(screenX)
          val y = Const.Projection.calcY(screenY)
          println(s"screenX: $screenX screenY: $screenY pointer: $pointer button: $button x: $x y: $y originWidth: ${Gdx.graphics.getWidth}")
          shared.player.state match {
            case UP | RUNNING =>
              if (!shared.player.coolDown) {
                onPlayerStateChanged(SHOOTING)
                shared.player.coolDown = true
                defer(Balance.playerCooldown, () => shared.player.state = UP)
                defer(Balance.weaponCooldown, () => shared.player.coolDown = false)
                onShoot(new Point(x, Const.Projection.height.toInt - y))
              } else {
                Assets.Audios.shootOut.play(1)
              }
            case _ =>
          }
          true
        } else {
          false
        }
      }
    })
  }

  override def run(delta: Float): Unit = {
    if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)) {
      if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        onMoveLeft()
      } else {
        onMoveRight()
      }
      shared.player.state match {
        case UP =>
          onPlayerStateChanged(RUNNING)
        case DOWN =>
          onPlayerStateChanged(CRAWLING)
        case _ =>
      }
    }
    if (Gdx.input.isKeyPressed(STAND_KEY)) {
      shared.player.state match {
        case UP =>
          onPlayerStateChanged(DOWN)
        case RUNNING =>
          onPlayerStateChanged(CRAWLING)
        case _ =>
      }
    } else {
      shared.player.state match {
        case DOWN =>
          onPlayerStateChanged(UP)
        case CRAWLING =>
          onPlayerStateChanged(RUNNING)
        case _ =>
      }
    }
  }

  override def onExit(): Unit = Gdx.input.setInputProcessor(null)
}

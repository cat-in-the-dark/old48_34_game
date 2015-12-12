package com.catinthedark.gban.units


import com.badlogic.gdx.{Input, InputAdapter, Gdx}

import com.catinthedark.gban.common.Const
import com.catinthedark.gban.view.{DOWN, UP, State}

import com.catinthedark.lib._
import org.lwjgl.util.Point

/**
  * Created by over on 22.01.15.
  */
abstract class Control(shared: Shared1) extends SimpleUnit with Deferred with Interval {
  val interval = 30f
  val onSitStand = new Pipe[State]()
  val onShoot = new Pipe[Point]()
  val onGameReload = new Pipe[Unit]()
  val onMoveLeft = new Pipe[Unit]()
  val onMoveRight = new Pipe[Unit]()
  
  val STAND_KEY = Input.Keys.CONTROL_LEFT

  override def onActivate() = {
    Gdx.input.setInputProcessor(new InputAdapter {
      override def keyDown(keycode: Int): Boolean = {
        keycode match {
          case STAND_KEY => onSitStand(DOWN)
          case Input.Keys.ESCAPE => onGameReload()
          case _ =>
        }
        true
      }

      override def keyUp(keycode: Int): Boolean = {
        if (keycode == STAND_KEY) {
          onSitStand(UP)
        }
        true
      }

      override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
        if (pointer == Input.Buttons.LEFT && !Gdx.input.isKeyPressed(STAND_KEY)) {
          val x = Const.Projection.calcX(screenX)
          val y = Const.Projection.calcY(screenY)
          println(s"screenX: $screenX screenY: $screenY pointer: $pointer button: $button x: $x y: $y originWidth: ${Gdx.graphics.getWidth}")
          onShoot(new Point(screenX, screenY))
          true
        } else {
          false
        }
      }
    })
  }

  override def run(delta: Float): Unit = {
    super.run(delta)
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      onMoveLeft()
    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      onMoveRight()
    }
  }

  override def onExit(): Unit = Gdx.input.setInputProcessor(null)
}

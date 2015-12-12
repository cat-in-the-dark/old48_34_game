package com.catinthedark.lib

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{TextureRegion, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{Matrix4, Rectangle}

class MagicSpriteBatch(debugOn: => Boolean) extends SpriteBatch {
  val debug = new ShapeRenderer()
  
  def drawWithDebug(t: TextureRegion, viewPos: Rectangle, physPos: Rectangle): Unit = {
    if (debugOn) debug.rect(physPos.x, physPos.y, physPos.width, physPos.height)
    else draw(t, viewPos.x, viewPos.y)
  }
  
  def drawWithDebug(t: Texture, viewPos: Rectangle, physPos: Rectangle): Unit = {
    if (debugOn) debug.rect(physPos.x, physPos.y, physPos.width, physPos.height)
    else draw(t, viewPos.x, viewPos.y)
  }

  def managed(f: MagicSpriteBatch => Unit): Unit = {
    debug.begin(ShapeType.Line)
    begin()
    f(this)
    end()
    debug.end()
  }

  override def setProjectionMatrix(projection: Matrix4): Unit = {
    super.setProjectionMatrix(projection)
    debug.setProjectionMatrix(projection)
  }
}

package com.catinthedark.gban.view

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2
import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.units.Shared1
import com.catinthedark.lib.Magic.richifySpriteBatch

class HudBar(val max: Int, vertical: Boolean = false) {
  def render(shapeRenderer: ShapeRenderer, value: Int, pos: Vector2, wh: Vector2): Unit = {
    val (w, h) = if (vertical) (wh.x, wh.y * value / max) else (wh.x * value / max, wh.y)
    shapeRenderer.rect(pos.x, pos.y, w, h)
  }
}
class Hud(shared: Shared1) {
  val batch = new SpriteBatch()
  val shapeRender = new ShapeRenderer()
  val barMyProgress = new HudBar(Const.Balance.maxProgress)
  val barEnemyProgress = new HudBar(Const.Balance.maxProgress)
  val barWater = new HudBar(Const.Balance.bucketVolume, true)

  def render(): Unit = {
    val player = shared.player

    shapeRender.begin(ShapeType.Filled)
    barMyProgress.render(shapeRender, Const.Balance.maxProgress, Const.HUD.myProgressPos(), Const.HUD.progressWh())
    barMyProgress.render(shapeRender, Const.Balance.maxProgress, Const.HUD.enemyProgressPos(), Const.HUD.progressWh())
    barWater.render(shapeRender, player.water, Const.HUD.waterBarPos(), Const.HUD.waterBarWh())
    shapeRender.end()

    batch managed { batch =>
      Assets.Fonts.hudFont.draw(batch, player.frags.toString, Const.HUD.myFragsPos().x, Const.HUD.myFragsPos().y)
      Assets.Fonts.hudFont.draw(batch, shared.enemy.frags.toString,
        Const.HUD.enemyFragsPos().x, Const.HUD.enemyFragsPos().y)
    }
  }

  def dispose(): Unit = {
    batch.dispose()
    shapeRender.dispose()
  }
}

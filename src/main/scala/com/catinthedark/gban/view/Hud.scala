package com.catinthedark.gban.view

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2
import com.catinthedark.gban.Assets
import com.catinthedark.gban.common.Const
import com.catinthedark.gban.units.Shared1
import com.catinthedark.lib.Magic.richifySpriteBatch

class HudBar(val max: Int) {
  def render(shapeRenderer: ShapeRenderer, value: Int, pos: Vector2, wh: Vector2): Unit = {
    shapeRenderer.rect(pos.x, pos.y, wh.x * value / max, wh.y)
  }
}
class Hud(shared: Shared1) {
  val batch = new SpriteBatch()
  val shapeRender = new ShapeRenderer()
  val barMyProgress = new HudBar(Const.Balance.maxProgress)
  val barEnemyProgress = new HudBar(Const.Balance.maxProgress)

  def render(): Unit = {
    shapeRender.begin(ShapeType.Filled)
    barMyProgress.render(shapeRender, Const.Balance.maxProgress, Const.HUD.myProgressPos(), Const.HUD.progressWh())
    barMyProgress.render(shapeRender, Const.Balance.maxProgress, Const.HUD.enemyProgressPos(), Const.HUD.progressWh())
    shapeRender.end()

    batch managed { batch =>
      Assets.Fonts.hudFont.draw(batch, "1", Const.HUD.myFragsPos().x, Const.HUD.myFragsPos().y)
      Assets.Fonts.hudFont.draw(batch, "0", Const.HUD.enemyFragsPos().x, Const.HUD.enemyFragsPos().y)
    }
  }

  def dispose(): Unit = {
    batch.dispose()
    shapeRender.dispose()
  }
}

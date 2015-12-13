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
    shapeRender.setColor(0.1f, 0.48f, 0.83f, 1)
    barMyProgress.render(shapeRender, player.progress, Const.HUD.myProgressPos(), Const.HUD.progressWh())
    barEnemyProgress.render(shapeRender, shared.enemy.progress, Const.HUD.enemyProgressPos(), Const.HUD.progressWh())
    barWater.render(shapeRender, player.water, Const.HUD.waterBarPos(), Const.HUD.waterBarWh())
    shapeRender.end()

    batch managed { batch =>
      batch.draw(if (shared.shared0.networkControl.isServer)
        Assets.Textures.upbarGood
      else Assets.Textures.upbarUgly, Const.UI.upbarPos().x, Const.UI.upbarPos().y)

      batch.draw(Assets.Textures.waterbat, Const.UI.waterbarPos().x, Const.UI.waterbarPos().y)

      Assets.Fonts.hudFont.draw(batch, player.frags.toString, Const.HUD.myFragsPos().x, Const.HUD.myFragsPos().y)
      Assets.Fonts.hudFont.draw(batch, shared.enemy.frags.toString,
        Const.HUD.enemyFragsPos().x, Const.HUD.enemyFragsPos().y)

      println(s"px = ${player.x}")
      println(s"plant pos = ${Const.UI.plantPos().x}")

      if (player.water != 0 &&
//        player.state == DOWN &&
        player.x + Const.UI.playerDownWH().x >= (Const.UI.plantPos().x - Const.UI.pumpEpsilon())) {
        println("ctrl 2")
        Assets.Fonts.ctrlFont.draw(batch, "press ctrl", Const.HUD.ctrl2Pos().x, Const.HUD.ctrl2Pos().y)
      }

      if (player.water != Const.Balance.bucketVolume &&
//        player.state == DOWN &&
        player.x < Const.UI.playerMinX() + Const.UI.pumpEpsilon()) {
        println("ctrl1 ")
        Assets.Fonts.ctrlFont.draw(batch, "press ctrl", Const.HUD.ctrl1Pos().x,Const.HUD.ctrl1Pos().y)
      }
    }
  }

  def dispose(): Unit = {
    batch.dispose()
    shapeRender.dispose()
  }
}

package com.catinthedark.gban.units

import com.catinthedark.gban.common.Const
import com.catinthedark.gban.entity.{Enemy, Player}
import com.catinthedark.gban.view.UP
import com.catinthedark.gban.{Assets, Shared0}


class Shared1(val shared0: Shared0) {
  val (player, enemy) =
    if (shared0.networkControl.isServer)
      (Player(Const.UI.playerMinX(), UP, 0, Assets.Animations.goodAnimations), Enemy(0, UP, 0, Assets.Animations.goodAnimations))
    else
      (Player(Const.UI.playerMinX(), UP, 0, Assets.Animations.goodAnimations), Enemy(0, UP, 0, Assets.Animations.goodAnimations))
}

package com.catinthedark.gban.units

import com.catinthedark.gban.{Assets, Shared0}
import com.catinthedark.gban.entity.{Enemy, Player}
import com.catinthedark.gban.network.NetworkServerControl
import com.catinthedark.gban.view.UP

class Shared1(val shared0: Shared0) {
  val (player, enemy) =
    if (shared0.networkControl.isServer)
      (Player(0, UP, 0, Assets.Animations.goodAnimations), Enemy(0, UP, 0, Assets.Animations.goodAnimations))
    else
      (Player(0, UP, 0, Assets.Animations.goodAnimations), Enemy(0, UP, 0, Assets.Animations.goodAnimations))
}

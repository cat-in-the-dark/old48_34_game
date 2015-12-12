package com.catinthedark.gban.units

import com.catinthedark.gban.Shared0
import com.catinthedark.gban.entity.{Enemy, Player}
import com.catinthedark.gban.view.UP

class Shared1(val shared0: Shared0,
              val player: Player = Player(0, UP, 0),
              val enemy: Enemy = Enemy(0, UP, 0))

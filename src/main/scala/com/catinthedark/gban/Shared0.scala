package com.catinthedark.gban

import com.catinthedark.gban.network.{NetworkServerControl, NetworkClientControl, NetworkControl}

/**
 * Created by over on 18.04.15.
 */
class Shared0(
  val serverAddress: String
) {
  val networkControl = if (serverAddress != null) {
    new NetworkClientControl(serverAddress)
  } else {
    new NetworkServerControl()
  }
}

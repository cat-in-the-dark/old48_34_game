package com.catinthedark.gban.network

import com.catinthedark.gban.common.Const
import org.zeromq.ZMQ.{Socket, Poller, PollItem}
import org.zeromq.{ZMQ, ZContext}

class NetworkServerControl extends NetworkControl {
  override def onHello(pushSocket: Socket) = pushSocket.send(s"$HELLO_PREFIX:")

  override def run(): Unit = {
    val ctx = new ZContext()
    val pullSocket = ctx.createSocket(ZMQ.PULL)
    val pushSocket = ctx.createSocket(ZMQ.PUSH)
    pullSocket.bind(s"tcp://*:${Const.serverPullPort}")
    pushSocket.bind(s"tcp://*:${Const.serverPushPort}")

    println("Start server")
    work(pushSocket, pullSocket)
  }

  override def isServer: Boolean = true
}

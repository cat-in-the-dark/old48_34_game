package com.catinthedark.gban.network

import com.catinthedark.gban.common.Const
import org.zeromq.ZMQ.{Poller, PollItem}
import org.zeromq.{ZMQ, ZContext}

class NetworkServerControl extends NetworkControl {

  override def run(): Unit = {
    println("Start listening")
    val ctx = new ZContext()
    val pullSocket = ctx.createSocket(ZMQ.PULL)
    val pushSocket = ctx.createSocket(ZMQ.PUSH)
    pullSocket.bind(s"tcp://*:${Const.serverPullPort}")
    pushSocket.bind(s"tcp://*:${Const.serverPushPort}")

    val pollItems = Array(new PollItem(pullSocket, Poller.POLLIN), new PollItem(pushSocket, Poller.POLLOUT))

    while (!Thread.currentThread().isInterrupted) {
      ZMQ.poll(pollItems, Const.pollTimeout)
      if (pollItems(0).isReadable) {
        val rawData = pullSocket.recvStr()
        println(s"Received data from client $rawData")
        val data = rawData.split(":")

        data(0) match {
          case MOVE_PREFIX =>
            val attrs = data(1).split(";")
            val x = attrs(0).toFloat
            val standUp = attrs(1).toBoolean
            moveListener(x, standUp)
          case SHOOT_PREFIX => shootListener()
          case ILOOSE_PREFIX => iLooseListener()
          case IWON_PREFIX => iWonListener()
          case HELLO_PREFIX => isConnected = Some()
          case _ => println(s"UPS, wrong prefix $rawData")
        }
      }
      
      if (!buffer.isEmpty && pollItems(1).isWritable) {
        pushSocket.send(buffer.poll())
      }
    }
    
    pullSocket.close()
    pushSocket.close()
    ctx.destroy()
  }
}

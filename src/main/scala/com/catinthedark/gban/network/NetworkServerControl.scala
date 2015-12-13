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
    var shouldStop: Boolean = false
    var receivedGameEnd: Boolean = false

    while (!shouldStop && !Thread.currentThread().isInterrupted) {
      try {
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
              onMove(x, standUp)
            case SHOOT_PREFIX =>
              val attrs = data(1).split(";")
              val exactly = attrs(0).toBoolean
              onShoot(exactly)
            case ILOOSE_PREFIX =>
              receivedGameEnd = true
              onILoose()
            case IWON_PREFIX =>
              receivedGameEnd = true
              onIWon()
            case HELLO_PREFIX =>
              pushSocket.send(s"$HELLO_PREFIX:")
              isConnected = Some()
            case _ => println(s"UPS, wrong prefix $rawData")
          }
        }

        if (!buffer.isEmpty && pollItems(1).isWritable) {
          pushSocket.send(buffer.poll())
        }
      } catch {
        case e: InterruptedException =>
          println("server interrupted")
          shouldStop = true
      }
    }

    if (!receivedGameEnd) {
      pushSocket.send(s"$IWON_PREFIX:")
    }

    pullSocket.close()
    pushSocket.close()
    println("Server connection closed")
  }

  override def isServer: Boolean = true
}

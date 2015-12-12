package com.catinthedark.lib

import scala.collection.mutable.ListBuffer

/**
 * Created by over on 29.01.15.
 */
case class Holder(val timeout: Long, val f: () => Unit)

trait LocalDeferred extends SimpleUnit with Deferred {
  private[this] var tasks = new ListBuffer[Holder]

  override def defer(timeout: Float, f: () => Unit) =
    tasks += Holder(System.currentTimeMillis() + (timeout * 1000).toLong, f)

  override def run(delay: Float) = {
    val now = System.currentTimeMillis()
    //make dirty things and than
    val needFilter = tasks.map(h =>
      if (now > h.timeout) {
        h.f()
        1
      }
      else 0
    ).sum != 0

    //remove executed tasks if exits
    if (needFilter)
      tasks = tasks.filter(h => now < h.timeout)

    super.run(delay)
  }
}

package org.risetopower.main

import org.newdawn.slick.AppGameContainer
import collection.immutable.Stream.cons
import org.risetopower.game.RiseToPowerStateBasedGame

object Main {
  def main(args: Array[String]) {
    val app = new AppGameContainer(new RiseToPowerStateBasedGame)

    app.setDisplayMode(800, 600, false)
    app.setVSync(true)
    app.start()
  }

}

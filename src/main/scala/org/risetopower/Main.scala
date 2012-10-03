package org.risetopower

import org.newdawn.slick.AppGameContainer
import org.risetopower.game.RiseToPowerStateBasedGame

object Main {
  def main(args: Array[String]) {

    val app = new AppGameContainer(new RiseToPowerStateBasedGame)

    app.setDisplayMode(800, 600, false)
    app.setVSync(true)
    app.start()
  }

}

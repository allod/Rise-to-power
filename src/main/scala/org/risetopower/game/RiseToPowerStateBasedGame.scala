package org.risetopower.game

import org.risetopower.menu.MainMenuState
import org.newdawn.slick.GameContainer
import org.risetopower.twl.TWLStateBasedGame
import java.net.URL

class RiseToPowerStateBasedGame extends TWLStateBasedGame("RiseToPowerStateBasedGame") {
  override def initStatesList(gameContainer:GameContainer) {
     addState (new MainMenuState)
  }

  override def themeURL : URL = getClass.getClassLoader.getResource("lesson1.xml")

}

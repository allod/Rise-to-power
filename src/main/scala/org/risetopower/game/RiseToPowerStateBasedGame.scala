package org.risetopower.game

import org.newdawn.slick.state.StateBasedGame
import org.risetopower.menu.MainMenuState
import org.newdawn.slick.GameContainer
import org.risetopower.twl.TWLStateBasedGame
import java.net.URL

object RiseToPowerStateConstants {
  val MAIN_MENU_ID = 0
}

class RiseToPowerStateBasedGame extends TWLStateBasedGame("RiseToPowerStateBasedGame") {
  override def initStatesList(gameContainer:GameContainer) {
     addState (new MainMenuState)
  }

  override def themeURL : URL = getClass.getClassLoader.getResource("inventory.xml")

}

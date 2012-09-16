package org.risetopower.game

import org.risetopower.menu.{OptionsMenuState, MainMenuState}
import org.newdawn.slick.GameContainer
import org.risetopower.twl.TWLStateBasedGame
import java.net.URL

class RiseToPowerStateBasedGame extends TWLStateBasedGame("RiseToPowerStateBasedGame") {
  override def initStatesList(gameContainer:GameContainer) {
     addState(new MainMenuState)
     addState(new OptionsMenuState)
  }

  override def themeURL : URL = getClass.getClassLoader.getResource("twl-theme/chutzpah.xml")

}

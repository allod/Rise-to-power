package org.risetopower.menu

import org.risetopower.twl.TWLFactory
import org.risetopower.game.RiseToPowerStateConstants
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import org.risetopower.twl.layout.TableLayout

class OptionsMenuState extends AbstractMenuGameState {
  override def getID = RiseToPowerStateConstants.OPTIONS_MENU_ID

  override def init(gameContainer: GameContainer, stateBasedGame: StateBasedGame) {
    logger.info("Initializing options menu")

    val gameOptionsButton = TWLFactory.createDefaultButton("menu.gameOptions")
    val graphicsOptionsButton = TWLFactory.createDefaultButton("menu.graphicOptions")
    val soundOptionsButton = TWLFactory.createDefaultButton("menu.soundOptions")
    val localizationOptionsButton = TWLFactory.createDefaultButton("menu.localizationOptions")
    val backButton = TWLFactory.createDefaultButton("menu.back")

    backButton.callback += {
      logger.info("Back to main menu")
      stateBasedGame.enterState(RiseToPowerStateConstants.MAIN_MENU_ID)
    }

    val layout = new TableLayout(5, 1, 1, 0.5) {
      setTheme("panel")
    }

    layout += gameOptionsButton
    layout += graphicsOptionsButton
    layout += soundOptionsButton
    layout += localizationOptionsButton
    layout += backButton

    panelLayout += layout
  }
}

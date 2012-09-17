package org.risetopower.menu

import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.risetopower.twl.TWLFactory
import org.risetopower.game.RiseToPowerStateConstants
import org.risetopower.twl.layout.TableLayout

class MainMenuState extends AbstractMenuGameState {
  override def getID = RiseToPowerStateConstants.MAIN_MENU_ID

  override def init(gameContainer: GameContainer, stateBasedGame: StateBasedGame) {
    logger.info("Initializing main menu")

    val newGameButton = TWLFactory.createDefaultButton("menu.newGame")
    val loadGameButton = TWLFactory.createDefaultButton("menu.loadGame")
    val optionsButton = TWLFactory.createDefaultButton("menu.options")
    val exitButton = TWLFactory.createDefaultButton("menu.exit")

    exitButton.callback += {
      logger.info("Game exit")
      gameContainer.exit()
    }

    optionsButton.callback += {
      logger.info("Go to option menu")
      stateBasedGame.enterState(RiseToPowerStateConstants.OPTIONS_MENU_ID)
    }

    val layout = new TableLayout(4, 1, 1, 0.5) {
       setTheme("panel")
    }

    layout += newGameButton
    layout += loadGameButton
    layout += optionsButton
    layout += exitButton

    panelLayout += layout

  }


}
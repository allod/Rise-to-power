package org.risetopower.menu

import org.risetopower.twl.{TWLFactory, BasicTWLGameState}
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
    val backButton = TWLFactory.createDefaultButton("menu.back")

    backButton.addCallback(() => {
      logger.info("Back to main menu")
      stateBasedGame.enterState(RiseToPowerStateConstants.MAIN_MENU_ID)
    })

    val layout = new TableLayout(4, 1, 1, 0.5) {
      setTheme("panel")
    }

    layout.add(gameOptionsButton)
    layout.add(graphicsOptionsButton)
    layout.add(soundOptionsButton)
    layout.add(backButton)

    panelLayout.add(layout)
  }
}

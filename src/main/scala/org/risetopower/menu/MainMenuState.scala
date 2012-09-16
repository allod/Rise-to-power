package org.risetopower.menu

import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.risetopower.twl.{TWLFactory, BasicTWLGameState}
import org.risetopower.game.RiseToPowerStateConstants
import de.matthiasmann.twl.ColumnLayout
import org.risetopower.twl.layout.{BorderLayout, TableLayout}
import org.risetopower.util.Logging

class MainMenuState extends BasicTWLGameState with Logging {
  override def getID = RiseToPowerStateConstants.MAIN_MENU_ID

  override def init(gameContainer: GameContainer, stateBasedGame: StateBasedGame) {
    logger.info("Initializing main menu")

    val newGameButton = TWLFactory.createDefaultButton("New game")
    val loadGameButton = TWLFactory.createDefaultButton("Load game")
    val optionsButton = TWLFactory.createDefaultButton("Options")
    val exitButton = TWLFactory.createDefaultButton("Exit")

    exitButton.addCallback(() => {
      logger.info("Game exit")
      gameContainer.exit()
    })

    val borderLayout = new BorderLayout(0.5, 0.3, 0, 0.3)

    val layout = new TableLayout(4, 1, 1, 0.5) {
       setTheme("panel")
    }

    layout.add(newGameButton)
    layout.add(loadGameButton)
    layout.add(optionsButton)
    layout.add(exitButton)

    borderLayout.add(layout)

    rootPane.add(borderLayout)
  }

  override def update(gameContainer: GameContainer, stateBasedGame: StateBasedGame, i: Int) {
    rootPane.getGUI.update()
  }

  override def render(gameContainer: GameContainer, stateBasedGame: StateBasedGame, graphics: Graphics) {
  }

  override def themeName : String = "backgroundPanel"

}
package org.risetopower.menu

import org.risetopower.twl.BasicTWLGameState
import org.risetopower.util.Logging
import org.newdawn.slick.{Graphics, GameContainer}
import org.newdawn.slick.state.StateBasedGame
import org.risetopower.twl.layout.BorderLayout

abstract class AbstractMenuGameState extends BasicTWLGameState with Logging {
  val panelLayout = new BorderLayout(0.5, 0.3, 0, 0.3)
  rootPane += panelLayout

  override def update(gameContainer: GameContainer, stateBasedGame: StateBasedGame, i: Int) {
    rootPane.getGUI.update()
  }

  override def render(gameContainer: GameContainer, stateBasedGame: StateBasedGame, graphics: Graphics) {
    // do nothing here
  }

  override def themeName = "backgroundPanel"




}

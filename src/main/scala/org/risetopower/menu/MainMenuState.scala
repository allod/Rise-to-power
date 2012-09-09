package org.risetopower.menu

import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import de.matthiasmann.twl._
import org.risetopower.twl.BasicTWLGameState
import org.risetopower.game.RiseToPowerStateConstants

class MainMenuState extends BasicTWLGameState {
  override def getID = RiseToPowerStateConstants.MAIN_MENU_ID

  val button = new Button("Epic button")

  override def init(gameContainer: GameContainer, stateBasedGame: StateBasedGame) {
    createButton()
    layout()
  }

  override def update(gameContainer: GameContainer, stateBasedGame: StateBasedGame, i: Int) {
    rootPane.getGUI.update()
  }

  override def render(gameContainer: GameContainer, stateBasedGame: StateBasedGame, graphics: Graphics) {
  }

  override def themeName : String = "lesson1"

  def createButton() {
    button.setTheme("button");
    rootPane.add(button);
  }

  def layout(){
    button.setPosition(100, 100);
    button.setSize(100, 33);
    //button.adjustSize();
  }

}
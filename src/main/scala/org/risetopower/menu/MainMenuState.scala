package org.risetopower.menu

import org.newdawn.slick.state.{StateBasedGame, BasicGameState}
import org.risetopower.game.RiseToPowerStateConstants
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.risetopower.BackgroundTest
import de.matthiasmann.twl.{Label, FPSCounter, Button, GUI}
import org.risetopower.twl.BasicTWLGameState
import de.matthiasmann.twl.renderer.{DynamicImage, Image}

class MainMenuState extends BasicTWLGameState {
 override def getID() = RiseToPowerStateConstants.MAIN_MENU_ID

  val fpsCounter = new FPSCounter()

  override def init(gameContainer: GameContainer, stateBasedGame: StateBasedGame) {
   rootPane.add(fpsCounter)



    // fpsCounter is bottom right
    fpsCounter.adjustSize()
    fpsCounter.setPosition(
      rootPane.getInnerWidth - fpsCounter.getWidth,
      rootPane.getInnerHeight - fpsCounter.getHeight)

    rootPane.layout()
 }

 override def update( gameContainer: GameContainer, stateBasedGame: StateBasedGame, i: Int) {

 }

 override def render(gameContainer:GameContainer, stateBasedGame:StateBasedGame, graphics:Graphics) {
 }
}

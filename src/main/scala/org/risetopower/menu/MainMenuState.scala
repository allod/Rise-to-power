package org.risetopower.menu

import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import de.matthiasmann.twl._
import org.risetopower.twl.BasicTWLGameState
import org.risetopower.game.RiseToPowerStateConstants

class MainMenuState extends BasicTWLGameState {
  override def getID = RiseToPowerStateConstants.MAIN_MENU_ID

  val frame = new ResizableFrame()
    val fpsCounter = new FPSCounter()

    val inventoryPanel = new InventoryPanel(10, 5)

  override def init(gameContainer: GameContainer, stateBasedGame: StateBasedGame) {

    frame.setTitle("Inventory")
    frame.setResizableAxis(ResizableFrame.ResizableAxis.NONE)
    frame.add(inventoryPanel)

    rootPane.add(fpsCounter)
    rootPane.add(frame)

    rootPane.validateLayout()
    positionFrame()

  }

  def positionFrame() {
    frame.adjustSize()
    frame.setPosition(
      rootPane.getInnerX + (rootPane.getInnerWidth - frame.getWidth) / 2,
      rootPane.getInnerY + (rootPane.getInnerHeight - frame.getHeight) / 2)
  }

  override def update(gameContainer: GameContainer, stateBasedGame: StateBasedGame, i: Int) {
    rootPane.getGUI.update()
  }

  override def render(gameContainer: GameContainer, stateBasedGame: StateBasedGame, graphics: Graphics) {
  }

  def themeName = ""

  /**
   * This method is called when the layout of the root pane needs to be updated.
   *
   * Widget position and size should only be changed within this method.
   *
   * @see Widget#setPosition(int, int)
   * @see Widget#setSize(int, int)
   * @see Widget#adjustSize()
   */
  override def layoutRootPane() {
    super.layoutRootPane()
    inventoryPanel.adjustSize()
    frame.adjustSize()
  }
}
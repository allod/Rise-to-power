package org.risetopower

import java.nio.{ByteOrder, ByteBuffer}
import de.matthiasmann.twl._
import de.matthiasmann.twl.renderer.Image
import org.lwjgl.opengl.GL11
import renderer.{DynamicImage, Renderer}
import util.Logging

class BackgroundTest extends DesktopArea with Logging {
  val fpsCounter = new FPSCounter()
  val mouseCoords = new Label()

  add(fpsCounter)
  add(mouseCoords)

  var gridBase: Image = _
  var gridMask: Image = _
  var lightImage: DynamicImage = _

  override def layout() {
    super.layout()

    // fpsCounter is bottom right
    fpsCounter.adjustSize()
    fpsCounter.setPosition(
      getInnerWidth - fpsCounter.getWidth,
      getInnerHeight - fpsCounter.getHeight)

    mouseCoords.adjustSize()
    mouseCoords.setPosition(0, getInnerHeight - fpsCounter.getHeight)
  }

  override def applyTheme(themeInfo: ThemeInfo) {
    super.applyTheme(themeInfo)
    gridBase = themeInfo.getImage("grid.base")
    gridMask = themeInfo.getImage("grid.mask")
  }

  override def paintBackground(gui: GUI) {
    if (lightImage == null) {
      createLightImage(gui.getRenderer)
    }
    if (gridBase != null && gridMask != null) {
      val time = gui.getCurrentTime % 2000
      val offset = time * (getInnerHeight + 2 * lightImage.getHeight) / 2000 - lightImage.getHeight
      gridBase.draw(getAnimationState, getInnerX, getInnerY, getInnerWidth, getInnerHeight)
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)
      lightImage.draw(getAnimationState, getInnerX, getInnerY + offset.toInt, getInnerWidth, lightImage.getHeight)
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      gridMask.draw(getAnimationState, getInnerX, getInnerY, getInnerWidth, getInnerHeight)
    }
  }

  private def createLightImage(renderer: Renderer) {
    lightImage = renderer.createDynamicImage(1, 128)
    val bb = ByteBuffer.allocateDirect(128 * 4)
    val ib = bb.order(ByteOrder.LITTLE_ENDIAN).asIntBuffer()
    for (i <- 0 until 128) {
      val value = 255 * math.sin(i * math.Pi / 127.0)
      ib.put(i, (value.toInt * 0x010101) | 0xFF000000)
    }
    lightImage.update(bb, DynamicImage.Format.BGRA)
  }

  override def handleEvent(evt: Event): Boolean = {
    if (evt.isMouseEvent) {
      mouseCoords.setText("x: " + evt.getMouseX + "  y: " + evt.getMouseY)
    }
    super.handleEvent(evt) || evt.isMouseEventNoWheel
  }
}
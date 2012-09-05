package org.risetopower

import de.matthiasmann.twl._
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer
import de.matthiasmann.twl.theme.ThemeManager
import org.lwjgl.input.{Keyboard, Mouse}
import org.lwjgl.opengl.{GL11, Display, DisplayMode}
import util.Logging

object HelloTWL extends Logging{

	// main method for HelloTWL example.
  def main(args: Array[String]) {
    logger.info("HelloTWL example started")

    try {
      Display.setDisplayMode(new DisplayMode(800, 600))
      Display.create()
      Display.setTitle("TWL Chat Demo")
      Display.setVSyncEnabled(true)

      val renderer = new LWJGLRenderer()
      val bgtest = new BackgroundTest()
      val gui = new GUI(bgtest, renderer)

      val theme = ThemeManager.createThemeManager(getClass.getClassLoader.getResource("bgtest.xml"), renderer)
      gui.applyTheme(theme)

      while (!Display.isCloseRequested) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)

        gui.update()
        Display.update()
        reduceInputLag()
      }

      gui.destroy()
      theme.destroy()
    } catch {
      case e: Exception => logger.error("Application terminated unexpectedly", e)
    }
    Display.destroy()
  }

  def reduceInputLag() {
    GL11.glGetError(); // this call will burn the time between vsyncs
    Display.processMessages(); // process new native messages since Display.update();
    Mouse.poll(); // now update Mouse events
    Keyboard.poll(); // and Keyboard too
  }
}

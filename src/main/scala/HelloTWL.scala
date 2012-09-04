import de.matthiasmann.twl._
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer
import de.matthiasmann.twl.theme.ThemeManager
import org.lwjgl.input.{Keyboard, Mouse}
import org.lwjgl.opengl.{GL11, Display, DisplayMode}

object HelloTWL {

  def main(args: Array[String]) {
    try {
      Display.setDisplayMode(new DisplayMode(800, 600))
      Display.create()
      Display.setTitle("TWL Chat Demo")
      Display.setVSyncEnabled(true)

      val renderer = new LWJGLRenderer()
      val bgtest = new BackgroundTest()
      val gui = new GUI(bgtest, renderer)

      val theme = ThemeManager.createThemeManager(getClass.getResource("bgtest.xml"), renderer)
      gui.applyTheme(theme)

      while(!Display.isCloseRequested) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)

        gui.update()
        Display.update()
        reduceInputLag()
      }

      gui.destroy()
      theme.destroy()
    } catch {
      case e: Exception => e.printStackTrace()
    }
    Display.destroy()
  }

  def reduceInputLag() {
    GL11.glGetError();          // this call will burn the time between vsyncs
    Display.processMessages();  // process new native messages since Display.update();
    Mouse.poll();               // now update Mouse events
    Keyboard.poll();            // and Keyboard too
  }
}

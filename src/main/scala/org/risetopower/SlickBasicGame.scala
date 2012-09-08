package org.risetopower
import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import org.newdawn.slick.Input

object SlickBasicGame {
  def main(args: Array[String]) {
    val app: AppGameContainer = new AppGameContainer(new SlickBasicGame)
    app.setDisplayMode(800, 600, false)
    app.start
  }
}

class SlickBasicGame extends BasicGame("Slick2D Path2Glory - SlickBasicGame") {
  var plane: Image = null
  var land: Image = null
  var x: Float = 400
  var y: Float = 300
  var scale: Float = 1

  def init(gc: GameContainer) {
    plane = new Image("plane.png")
    land = new Image("land.jpg")
  }

  def update(gc: GameContainer, delta: Int) {
    val input: Input = gc.getInput
    if (input.isKeyDown(Input.KEY_A)) {
      plane.rotate(-0.2f * delta)
    }
    if (input.isKeyDown(Input.KEY_D)) {
      plane.rotate(0.2f * delta)
    }
    if (input.isKeyDown(Input.KEY_W)) {
      val hip: Float = 0.4f * delta
      val rotation: Float = plane.getRotation
      x += hip * math.sin(math.toRadians(rotation)).toFloat
      y -= hip * math.cos(math.toRadians(rotation)).toFloat
    }
    if (input.isKeyDown(Input.KEY_2)) {
      val tt = if ((scale >= 5.0f)) 0 else 0.1f
      scale += tt
      plane.setCenterOfRotation(plane.getWidth / 2.0f * scale, plane.getHeight / 2.0f * scale)
    }
    if (input.isKeyDown(Input.KEY_1)) {
      val tt = if ((scale <= 1.0f)) 0 else 0.1f
      scale -= tt
      plane.setCenterOfRotation(plane.getWidth / 2.0f * scale, plane.getHeight / 2.0f * scale)
    }
  }

  def render(gc: GameContainer, g: Graphics) {
    land.draw(0, 0)
    plane.draw(x, y, scale)
  }


}
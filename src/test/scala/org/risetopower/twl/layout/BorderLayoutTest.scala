package org.risetopower.twl.layout

import org.scalatest.FunSuite
import de.matthiasmann.twl.Button

class BorderLayoutTest extends FunSuite {
  test("Border component placement") {
    val layout = new BorderLayout(0.1, 0.5, 0.3, 0.4)
    val button = new Button

    layout.setSize(100, 200)
    layout.add(button)
    layout.layoutChildren()

    // x = 100 * 0.4 = 40
    // y = 200 * 0.1 = 20
    // height = 200 * (1 - 0.4) = 120
    // width = 100 * (1 - 0.9) = 10

    assert(button.getX === 40)
    assert(button.getY === 20)
    assert(button.getHeight === 120)
    assert(button.getWidth === 10)

  }
}

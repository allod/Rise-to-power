package org.risetopower

import org.scalatest.FunSuite
import twl.layout.TableLayout
import de.matthiasmann.twl.Button

class TableLayoutTest extends FunSuite {
  test("table layout component placement") {
    val layout = new TableLayout(2,3,0.5, 1)
    val widgets = List(new Button, new Button, new Button,
      new Button, new Button, new Button)

    widgets foreach(layout.add(_))
    layout.setSize(180, 900)
    layout.layoutChildren()

    // 180 in width, 3 columns and 100% gap,
    // this means that column width is 180 / 3 / 2 = 30
    // 900 in height, 2 rows with 50% gap,
    // this means that row height is 900 / 2 /1.5 = 300
    // x gap = 30 / 2 = 15
    // y gap = 300 / 2 / 2 = 75

    val width = 30
    val height = 300

    val widthGap = 15
    val heightGap = 75

    widgets foreach (w => {
        assert(w.getWidth === width)
        assert(w.getHeight === height)
      }
    )

    assert(widgets(0).getX === widthGap)
    assert(widgets(0).getY === heightGap)
    assert(widgets(1).getX === width + 3 * widthGap)
    assert(widgets(1).getY === heightGap)
    assert(widgets(2).getX === 2 * width + 5 * widthGap)
    assert(widgets(2).getY === heightGap)
    assert(widgets(3).getX === widthGap)
    assert(widgets(3).getY === height + 3 * heightGap)
    assert(widgets(4).getX === width + 3 * widthGap)
    assert(widgets(4).getY === height + 3 * heightGap)
    assert(widgets(5).getX === 2 * width + 5 * widthGap)
    assert(widgets(5).getY === height + 3 * heightGap)
  }
}

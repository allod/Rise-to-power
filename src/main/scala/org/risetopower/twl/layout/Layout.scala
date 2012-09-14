package org.risetopower.twl.layout

import de.matthiasmann.twl.Widget

/**
 * Abstract class for all layouts. Override method layoutComponents()
 * to add layout logic, and use children() method to retrieve widgets list
 *
 */
abstract class Layout extends Widget {

  def layoutChildren()

  private[layout] def children() : List[Widget] = {
      val res = for (i <- 0 until getNumChildren) yield getChild(i)
      res.toList
  }

  override def layout() {
    setSize(getParent.getInnerWidth, getParent.getInnerHeight)
    setPosition(getParent.getInnerX, getParent.getInnerY)
    layoutChildren()
  }
}

package org.risetopower.twl.layout

import de.matthiasmann.twl.Widget
import org.risetopower.twl.wrappers.WidgetWrapper

/**
 * Abstract class for all layouts. Override method layoutComponents()
 * to add layout logic, and use children() method to retrieve widgets list
 *
 */
abstract class Layout extends Widget with WidgetWrapper {

  def layoutChildren()

  /**
   * have to throw exception if there is more children than layout can handle
   */
  def checkChildrenCount()

  override def layout() {
    checkChildrenCount()

    // no need to occupy parent's size if parent is layout,
    // layout will set you your correct size
    if (parent.isDefined && !parent.get.isInstanceOf[Layout]) {
      setSize(parent.get.getInnerWidth, parent.get.getInnerHeight)
      setPosition(parent.get.getInnerX, parent.get.getInnerY)
    }

    layoutChildren()
    correctChildrenPosition()
  }

  private[layout] def children = (0 until getNumChildren).map(getChild(_))

  private def correctChildrenPosition() {
    val xCorrection = getInnerX
    val yCorrection = getInnerY

    for (child <- children) {
      child.setPosition(child.getX + xCorrection, child.getY + yCorrection)
    }
  }
}

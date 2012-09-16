package org.risetopower.twl.layout

import de.matthiasmann.twl.Widget
import org.risetopower.util.Logging

/**
 * Abstract class for all layouts. Override method layoutComponents()
 * to add layout logic, and use children() method to retrieve widgets list
 *
 */
abstract class Layout extends Widget {

  def layoutChildren()

  def parentWidget : Option[Widget] = {
    if (getParent == null) {
      None
    } else {
      Some(getParent)
    }
  }

  /**
   * have to throw exception if there is more children than layout can handle
   */
  def checkChildrenCount()

  private[layout] def children : List[Widget] = {
      val res = for (i <- 0 until getNumChildren) yield getChild(i)
      res.toList
  }

  override def layout() {
    checkChildrenCount()

    // no need to occupy parent's size if parent is layout,
    // layout will set you your correct size
    if (parentWidget.isDefined && !parentWidget.get.isInstanceOf[Layout]) {
      setSize(parentWidget.get.getInnerWidth, parentWidget.get.getInnerHeight)
      setPosition(parentWidget.get.getInnerX, parentWidget.get.getInnerY)
    }

    layoutChildren()
    correctChildrenPosition()
  }

  private def correctChildrenPosition() {
    val xCorrection = getInnerX
    val yCorrection = getInnerY

    for (child <- children) {
      child.setPosition(child.getX + xCorrection, child.getY + yCorrection)
    }
  }
}

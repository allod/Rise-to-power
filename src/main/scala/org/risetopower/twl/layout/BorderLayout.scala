package org.risetopower.twl.layout

/**
 * Idea of border layout is to have one component which doesn't occupy
 * full parent component but only part of it
 *
 * params order is like in css - TRoBLe
 *
 * @param top percentage from top border
 * @param right percentage from right border
 * @param bottom percentage from bottom border
 * @param left percentage from left border

 */
class BorderLayout(top:Double, right:Double, bottom:Double, left:Double) extends Layout {
  require(top >= 0 && top <= 1, "Top parameter is invalid, it is less than zero or bigger than one")
  require(bottom >= 0 && bottom <= 1, "Bottom parameter is invalid, it is less than zero or bigger than one")
  require(left >= 0 && left <= 1, "Left parameter is invalid, it is less than zero or bigger than one")
  require(right >= 0 && right <= 1, "Right parameter is invalid, it is less than zero or bigger than one")
  require(right + left < 1, "Right and left parameters sum is bigger or equal than 1")
  require(top + bottom < 1, "Top and bottom parameters sum is bigger or equal than 1")

  def layoutChildren() {
    if (children.size == 0) {
      return
    }

    val topPixels = (getHeight * top).round.toInt
    val bottomPixels = (getHeight * bottom).round.toInt
    val leftPixels = (getWidth * left).round.toInt
    val rightPixels = (getWidth * right).round.toInt

    val x = leftPixels
    val y = topPixels
    val width = getWidth - leftPixels - rightPixels
    val height = getHeight - topPixels - bottomPixels

    val child = children(0)

    child.setSize(width, height)
    child.setPosition(x, y)
  }

  override def checkChildrenCount() {
    require(children.size <= 1, "More than one child in BorderLayout!")
  }
}

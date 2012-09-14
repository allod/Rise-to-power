package org.risetopower.twl.layout

import de.matthiasmann.twl.Widget

/**
 *
 * @param rows table rows
 * @param cols table columns
 * @param rowGap difference between columns. 1.0 is for gaps that equals
 *               to row length, 0.5 for gaps that are it two times smaller
 * @param colGap same as rowGap but related to columns
 */
class TableLayout(val rows:Int, val cols:Int, val rowGap:Double, val colGap:Double) extends Layout {
  override def layoutChildren() {

     val width = getWidth.toDouble
     val height = getHeight.toDouble
     val childWidthWithoutGap = (width / cols).toInt
     val childHeightWithoutGap = (height / rows).toInt

     var i = 0
     for (row <- 0 until rows) {
       for (col <- 0 until cols) {
         val xWithoutGap = (col * width / cols).round.toInt
         val yWithoutGap = (row * height / rows).round.toInt

         val childWidthWithGap = (childWidthWithoutGap / (1 + colGap)).round.toInt
         val childHeightWithGap = (childHeightWithoutGap / (1 + rowGap)).round.toInt

         val x = xWithoutGap + (childWidthWithoutGap - childWidthWithGap) / 2
         val y = yWithoutGap + (childHeightWithoutGap - childHeightWithGap) / 2

         children()(i).setPosition(x, y)

         children()(i).setSize(childWidthWithGap, childHeightWithGap)
         i+=1
       }
     }

  }
}

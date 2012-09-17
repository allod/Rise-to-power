package org.risetopower.twl.wrappers

import de.matthiasmann.twl.Widget

trait WidgetWrapper {
  this: Widget =>

  def parent = Option(getParent)

  def theme = getTheme

  def theme_=(theme: String) {
    setTheme(theme)
  }

  def +=(child: Widget) {
    add(child)
  }


}

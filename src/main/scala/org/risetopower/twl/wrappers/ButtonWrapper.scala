package org.risetopower.twl.wrappers

import de.matthiasmann.twl.Button

trait ButtonWrapper extends WidgetWrapper {
  this: Button =>

  val callback = new {
    def +=(fun: => Unit) {
      addCallback(new Runnable() {
        def run() = fun
      })
    }
  }
}

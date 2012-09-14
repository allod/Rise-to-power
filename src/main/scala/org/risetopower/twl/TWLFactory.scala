package org.risetopower.twl

import de.matthiasmann.twl.Button

object TWLFactory {
  def createDefaultButton(text:String) : Button = {
     val button = new Button(text)
     button.setTheme("button")
     button
  }
}

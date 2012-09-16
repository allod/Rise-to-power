package org.risetopower.twl

import de.matthiasmann.twl.Button
import org.risetopower.localization.Messages

object TWLFactory {

  def createDefaultButton(key:String) : Button = {
     val button = new Button(Messages.get(key))
     button.setTheme("button")
     button
  }
}

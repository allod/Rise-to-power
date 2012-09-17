package org.risetopower.twl

import de.matthiasmann.twl.Button
import org.risetopower.localization.Messages
import wrappers.ButtonWrapper

object TWLFactory {

  def createDefaultButton(key:String) = new Button(Messages.get(key)) with ButtonWrapper {
    theme = "button"
  }
}

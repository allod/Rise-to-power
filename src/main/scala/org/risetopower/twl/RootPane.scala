package org.risetopower.twl

/*
 * Copyright (c) 2008-2010, Matthias Mann
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Matthias Mann nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import de.matthiasmann.twl.DesktopArea
import de.matthiasmann.twl.Event
import de.matthiasmann.twl.Widget
import org.risetopower.util.Logging
import de.matthiasmann.twl.Event.Type

/**
 * RootPane for all game states.
 * It forwards input events which where not handled by the UI to the game state.
 *
 * @author Matthias Mann
 */
class RootPane(val state:BasicTWLGameState) extends DesktopArea with Logging {
  setCanAcceptKeyboardFocus(true)

  var oldMouseX:Int = 0
  var oldMouseY:Int = 0

  /**
   * When subclassing this class it's strongly suggested to provide
   * a default constructor to allow previewing in the Theme Editor.
   */
  def this() {
    this(null)
  }

  /**
   * Returns true when the root pane is in preview mode (Theme Editor).
   * @return true when the root pane is in preview mode (Theme Editor).
   */
  def previewMode() = state == null

  override def keyboardFocusLost() {
    if(state != null) {
      state.keyboardFocusLost()
    }
  }

  override def requestKeyboardFocus(child:Widget):Boolean = {
    if (child != null && state != null) {
      state.keyboardFocusLost()
    }

    super.requestKeyboardFocus(child)
  }

  override def handleEvent(evt:Event ):Boolean = {
    if (super.handleEvent(evt)) {
      true
    }

    if(state != null) {
      evt.getType match {
        case Type.KEY_PRESSED => state.keyPressed(evt.getKeyCode, evt.getKeyChar)
        case Type.KEY_RELEASED => state.keyReleased(evt.getKeyCode, evt.getKeyChar)
        case Type.MOUSE_BTNDOWN => state.mousePressed(evt.getMouseButton, evt.getMouseX, evt.getMouseY)
        case Type.MOUSE_BTNUP => state.mouseReleased(evt.getMouseButton, evt.getMouseX, evt.getMouseY)
        case Type.MOUSE_CLICKED => state.mouseClicked(evt.getMouseButton, evt.getMouseX, evt.getMouseY, evt.getMouseClickCount)
        case Type.MOUSE_ENTERED | Type.MOUSE_MOVED => state.mouseMoved(oldMouseX, oldMouseY, evt.getMouseX, evt.getMouseY)
        case Type.MOUSE_DRAGGED => state.mouseDragged(oldMouseX, oldMouseY, evt.getMouseX, evt.getMouseY)
        case Type.MOUSE_WHEEL => state.mouseWheelMoved(evt.getMouseWheelDelta)
      }
    }

    if (evt.isMouseEvent) {
      oldMouseX = evt.getMouseX
      oldMouseY = evt.getMouseY
    }
    true
  }

  override def layout() {
    super.layout()
    state.layoutRootPane()
  }
}


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

import de.matthiasmann.twl.GUI
import de.matthiasmann.twl.Widget
import de.matthiasmann.twl.renderer.Renderer
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer
import de.matthiasmann.twl.theme.ThemeManager
import org.lwjgl.opengl.GL11
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException
import org.newdawn.slick.state.GameState
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.state.transition.Transition
import java.net.URL
/**
 * A StateBaseGame subclass with support for a TWL Ui per state.
 *
 * @author Matthias Mann
 */
abstract class TWLStateBasedGame(name:String) extends StateBasedGame(name) {

  val emptyRootWidget:Widget = new Widget()
  emptyRootWidget.setTheme("")
  var gui:GUI = _
  var guiInitialized:Boolean = false

  /**
   * Adds a new game state
   * @param state the game state
   * @see StateBasedGame#addState(org.newdawn.slick.state.GameState)
   */
  def addState(state:BasicTWLGameState) {
    super.addState(state)
  }

  /**
   * Adds a new game state.
   *
   * This method is overriden to ensure that only instances of BasicTWLGameState are added.
   *
   * @param state the game state. Must be an instance of BasicTWLGameState
   * @see StateBasedGame#addState(org.newdawn.slick.state.GameState)
   */
  override def addState(state:GameState) {
    if(!(state.isInstanceOf[BasicTWLGameState])) {
      throw new IllegalArgumentException("state must be a BasicTWLGameState")
    }
    super.addState(state)
  }

  /**
   * Implement this method and return the URL for the TWL theme.
   *
   * @return the URL for the TWL theme. Must not be null.
   */
  def themeURL:URL

  /**
   * Transits to a the specified game state.
   * This method hides the UI of the current state before starting the transition.
   *
   * @param id The ID of the state to enter
   * @param leave The transition to use when leaving the current state
   * @param enter The transition to use when entering the new state
   * @see StateBasedGame#enterState(int, org.newdawn.slick.state.transition.Transition, org.newdawn.slick.state.transition.Transition)
   */
  override def enterState(id:Int, leave:Transition , enter:Transition ) {
    if(gui != null) {
      gui.setRootPane(emptyRootWidget)
    }
    super.enterState(id, leave, enter)
  }

  def loadTheme(renderer:Renderer):ThemeManager = {
    assert(themeURL != null)
    ThemeManager.createThemeManager(themeURL, renderer)
  }

  def rootPane = gui.getRootPane.asInstanceOf[RootPane]

  def rootPane_=(rootPane:RootPane) {
    if(!guiInitialized) {
      guiInitialized = true
      initGUI()
    }
    if(gui != null) {
      gui.setRootPane(rootPane)
    }
  }

  def initGUI() {
    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
    try {
      val renderer = new LWJGLRenderer()
      val theme = loadTheme(renderer)

      gui = new GUI(emptyRootWidget, renderer, null)
      gui.applyTheme(theme)

      val input = getContainer.getInput
      val inputAdapter = new TWLInputAdapter(gui, input)
      input.addPrimaryListener(inputAdapter)
    } catch {
      case e:Throwable =>
      throw new SlickException("Could not initialize TWL GUI", e)
    } finally {
      GL11.glPopAttrib()
    }
  }

  override def postRenderState(container:GameContainer, g:Graphics) {
    if(gui != null) {
      gui.draw()
    }
  }

  override def postUpdateState(container:GameContainer, delta:Int) {
    if(gui != null) {
      gui.setSize()
      gui.handleTooltips()
      gui.updateTimers()
      gui.invokeRunables()
      gui.validateLayout()
      gui.setCursor()
    }
  }
}


package org.risetopower.menu

import de.matthiasmann.twl.renderer.AnimationState.StateKey
import de.matthiasmann.twl._
import de.matthiasmann.twl.renderer.Image

object ItemSlot {
  val STATE_DRAG_ACTIVE = StateKey.get("dragActive")
  val STATE_DROP_OK = StateKey.get("dropOk")
  val STATE_DROP_BLOCKED = StateKey.get("dropBlocked")

}

class ItemSlot extends Widget {

  private[this] var _item: String = _
  var icon: Image = _
  var listener: DragListener = _
  var dragActive: Boolean = _
  var icons: ParameterMap = _

  def item = _item

  def item_=(item: String) {
    _item = item
    findIcon()
  }

  def canDrop: Boolean = item == null

  def setDropState(drop: Boolean, ok: Boolean) {
    val as = getAnimationState
    as.setAnimationState(ItemSlot.STATE_DROP_OK, drop && ok)
    as.setAnimationState(ItemSlot.STATE_DROP_BLOCKED, drop && !ok)
  }

  override def handleEvent(evt: Event): Boolean = {
    if (evt.isMouseEventNoWheel) {
      if (dragActive) {
        if (evt.isMouseDragEnd) {
          if (listener != null) {
            listener.dragStopped(this, evt)
          }
          dragActive = false
          getAnimationState.setAnimationState(ItemSlot.STATE_DRAG_ACTIVE, false)
        } else if (listener != null) {
          listener.dragging(this, evt)
        }
      } else if (evt.isMouseDragEvent) {
        dragActive = true
        getAnimationState.setAnimationState(ItemSlot.STATE_DRAG_ACTIVE, true)
        if (listener != null) {
          listener.dragStarted(this, evt)
        }
      }
      return true
    }


    super.handleEvent(evt)
  }

  override def paintWidget(gui: GUI) {
    if (!dragActive && icon != null) {
      icon.draw(getAnimationState, getInnerX, getInnerY, getInnerWidth, getInnerHeight)
    }
  }

  override def paintDragOverlay(gui: GUI, mouseX: Int, mouseY: Int, modifier: Int) {
    if (icon != null) {
      val innerWidth = getInnerWidth
      val innerHeight = getInnerHeight
      icon.draw(getAnimationState,
        mouseX - innerWidth / 2,
        mouseY - innerHeight / 2,
        innerWidth, innerHeight)
    }
  }

  override def applyTheme(themeInfo: ThemeInfo) {
    super.applyTheme(themeInfo)
    icons = themeInfo.getParameterMap("icons")
    findIcon()
  }

  def findIcon() {
    if (item == null || icons == null) {
      icon = null
    } else {
      icon = icons.getImage(item)
    }
  }
}
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

  private[this] var _item: Option[String] = None
  var icons: Option[ParameterMap] = None
  var icon: Option[Image] = None
  var listener: DragListener = _
  var dragActive: Boolean = _

  def item = _item

  def item_=(item: Option[String]) {
    _item = item
    findIcon()
  }

  def canDrop: Boolean = item == None

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
    for (i <- icon; if !dragActive ) {
      i.draw(getAnimationState, getInnerX, getInnerY, getInnerWidth, getInnerHeight)
    }
  }

  override def paintDragOverlay(gui: GUI, mouseX: Int, mouseY: Int, modifier: Int) {
    icon foreach { value =>
      val x: Int = mouseX - getInnerWidth / 2
      val y: Int = mouseY - getInnerHeight / 2
      value.draw(getAnimationState, x, y, getInnerWidth, getInnerHeight)
    }
  }

  override def applyTheme(themeInfo: ThemeInfo) {
    super.applyTheme(themeInfo)
    icons = Option(themeInfo.getParameterMap("icons"))
    findIcon()
  }

  def findIcon() {
    icon = (icons, item) match {
      case (Some(iconsValue), Some(itemValue)) => Option(iconsValue.getImage(itemValue))
      case _ => None
    }
  }
}
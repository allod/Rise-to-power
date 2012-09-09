package org.risetopower.menu

import de.matthiasmann.twl.{ThemeInfo, Event, Widget}

class InventoryPanel(var numSlotsX: Int, var numSlotsY: Int) extends Widget {

  var slot = new Array[ItemSlot](numSlotsX * numSlotsY)

  var slotSpacing: Int = _

  var dragSlot: ItemSlot = _
  var dropSlot: ItemSlot = _

  var listener = new DragListener() {
    def dragStarted(slot: ItemSlot, evt: Event) {
      InventoryPanel.this.dragStarted(slot, evt)
    }

    def dragging(slot: ItemSlot, evt: Event) {
      InventoryPanel.this.dragging(slot, evt)
    }

    def dragStopped(slot: ItemSlot, evt: Event) {
      InventoryPanel.this.dragStopped(slot, evt)
    }
  }

  for (i <- 0 until slot.length) {
    slot(i) = new ItemSlot()
    slot(i).listener = listener
    add(slot(i))
  }

  slot(0).item = "red"
  slot(1).item = "green"
  slot(2).item = "blue"
  slot(3).item = "yellow"


  override def getPreferredInnerWidth: Int = (slot(0).getPreferredWidth + slotSpacing) * numSlotsX - slotSpacing

  override def getPreferredInnerHeight: Int = (slot(0).getPreferredHeight + slotSpacing) * numSlotsY - slotSpacing

  override def layout() {
    val slotWidth = slot(0).getPreferredWidth
    val slotHeight = slot(0).getPreferredHeight

    var y = getInnerY

    for (row <- 0 until numSlotsY) {
      var i = 0
      for (col <- 0 until numSlotsX) {
        i += 1
        var x = getInnerX
        slot(i).adjustSize()
        slot(i).setPosition(x, y)
        x += slotWidth + slotSpacing
      }
      y += slotHeight + slotSpacing
    }
  }

  override def applyTheme(themeInfo: ThemeInfo) {
    super.applyTheme(themeInfo)
    slotSpacing = themeInfo.getParameter("slotSpacing", 5)
  }

  def dragStarted(slot: ItemSlot, evt: Event) {
    if (slot.item != null) {
      dragSlot = slot
      dragging(slot, evt)
    }
  }

  def dragging(slot: ItemSlot, evt: Event) {
    if (dragSlot != null) {
      val w = getWidgetAt(evt.getMouseX, evt.getMouseY)
      if (w.isInstanceOf[ItemSlot]) {
        setDropSlot(w.asInstanceOf[ItemSlot])
      } else {
        setDropSlot(null)
      }
    }
  }

  def dragStopped(slot: ItemSlot, evt: Event) {
    if (dragSlot != null) {
      dragging(slot, evt)
      if (dropSlot != null && dropSlot.canDrop && dropSlot != dragSlot) {
        dropSlot.item = dragSlot.item
        dragSlot.item = null
      }
      setDropSlot(null)
      dragSlot = null
    }
  }

  def setDropSlot(slot: ItemSlot) {
    if (slot != dropSlot) {
      if (dropSlot != null) {
        dropSlot.setDropState(drop = false, ok = false)
      }
      dropSlot = slot
      if (dropSlot != null) {
        dropSlot.setDropState(drop = true, ok = dropSlot == dragSlot || dropSlot.canDrop)
      }
    }
  }

}

trait DragListener {
  def dragStarted(slot: ItemSlot, evt: Event)

  def dragging(slot: ItemSlot, evt: Event)

  def dragStopped(slot: ItemSlot, evt: Event)
}

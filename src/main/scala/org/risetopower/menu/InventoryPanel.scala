package org.risetopower.menu

import de.matthiasmann.twl.Event
import de.matthiasmann.twl.ThemeInfo
import de.matthiasmann.twl.Widget

class InventoryPanel(numSlotsX: Int, numSlotsY: Int) extends Widget {
  private val slots = new Array[ItemSlot](numSlotsX * numSlotsY)
  private var slotSpacing: Int = 0
  private var dragSlot: ItemSlot = _
  private var dropSlot: ItemSlot = _

  val listener: DragListener = createDragListener()

  for(i <- 0 until slots.length) {
    slots(i) = new ItemSlot
    slots(i).listener = listener
    add(slots(i))
  }
  slots(0).item = "red"
  slots(1).item = "green"
  slots(2).item = "blue"
  slots(3).item = "yellow"

  override def getPreferredInnerWidth: Int = (slots(0).getPreferredWidth + slotSpacing) * numSlotsX - slotSpacing

  override def getPreferredInnerHeight: Int = (slots(0).getPreferredHeight + slotSpacing) * numSlotsY - slotSpacing

  protected override def layout() {
    val slotWidth: Int = slots(0).getPreferredWidth
    val slotHeight: Int = slots(0).getPreferredHeight
    var y: Int = getInnerY
    var i: Int = 0
    for (row <- 0 until numSlotsY) {
      var x: Int = getInnerX
      for (col <- 0 until numSlotsX) {
        slots(i).adjustSize()
        slots(i).setPosition(x, y)
        x += slotWidth + slotSpacing
        i += 1
      }
      y += slotHeight + slotSpacing
    }
  }

  protected override def applyTheme(themeInfo: ThemeInfo) {
    super.applyTheme(themeInfo)
    slotSpacing = themeInfo.getParameter("slotSpacing", 5)
  }

  private[menu] def dragStarted(slot: ItemSlot, evt: Event) {
    if (slot.item != null) {
      dragSlot = slot
      dragging(slot, evt)
    }
  }

  private[menu] def dragging(slot: ItemSlot, evt: Event) {
    if (dragSlot != null) {
      val w: Widget = getWidgetAt(evt.getMouseX, evt.getMouseY)
      if (w.isInstanceOf[ItemSlot]) {
        setDropSlot(w.asInstanceOf[ItemSlot])
      }
      else {
        setDropSlot(null)
      }
    }
  }

  private[menu] def dragStopped(slot: ItemSlot, evt: Event) {
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

  private def setDropSlot(slot: ItemSlot) {
    if (slot ne dropSlot) {
      if (dropSlot != null) {
        dropSlot.setDropState(drop = false, ok = false)
      }
      dropSlot = slot
      if (dropSlot != null) {
        dropSlot.setDropState(drop = true, ok = (dropSlot eq dragSlot) || dropSlot.canDrop)
      }
    }
  }

  def createDragListener() = {
    new DragListener {
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
  }
}

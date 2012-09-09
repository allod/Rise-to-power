package org.risetopower.menu

import de.matthiasmann.twl.Event
import de.matthiasmann.twl.ThemeInfo
import de.matthiasmann.twl.Widget

class InventoryPanel(numSlotsX: Int, numSlotsY: Int) extends Widget {
  private val slots = new Array[ItemSlot](numSlotsX * numSlotsY)
  private var slotSpacing: Int = 0
  private var dragSlot: Option[ItemSlot] = None
  private var dropSlot: Option[ItemSlot] = None

  val listener: DragListener = createDragListener()

  for(i <- 0 until slots.length) {
    slots(i) = new ItemSlot
    slots(i).listener = listener
    add(slots(i))
  }
  slots(0).item = Some("red")
  slots(1).item = Some("green")
  slots(2).item = Some("blue")
  slots(3).item = Some("yellow")

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
    slot.item foreach {_ =>
      dragSlot = Option(slot) //TODO  consider making slot option
      dragging(slot, evt)
    }
  }

  private[menu] def dragging(slot: ItemSlot, evt: Event) {
    dragSlot foreach { _ =>
      val w: Widget = getWidgetAt(evt.getMouseX, evt.getMouseY)
      if (w.isInstanceOf[ItemSlot]) {
        setDropSlot(w.asInstanceOf[ItemSlot])
      } else {
        setDropSlot(null)
      }
    }
  }

  private[menu] def dragStopped(slot: ItemSlot, evt: Event) {
    dragSlot foreach { _ =>
      dragging(slot, evt)
      for (dropSlotValue <- dropSlot; dragSlotValue <- dragSlot
           if dropSlotValue != dragSlotValue
           if dropSlotValue.canDrop) {
        dropSlotValue.item = dragSlotValue.item
        dragSlotValue.item = None
      }
      setDropSlot(null)
      dragSlot = None
    }
  }

  //TODO refactorme
  private def setDropSlot(slot: ItemSlot) {
    val dropSlotValue = dropSlot getOrElse null
    if (slot ne dropSlotValue) {
      if (dropSlotValue != null) {
        dropSlotValue.setDropState(drop = false, ok = false)
      }
      dropSlot = Option(slot)
      if (slot != null) {
        val ok = dragSlot match {
          case Some(y) => slot.canDrop  || (slot eq y)
          case None => slot.canDrop
        }
        slot.setDropState(drop = true, ok = ok)
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

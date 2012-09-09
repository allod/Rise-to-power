package org.risetopower.menu

import de.matthiasmann.twl.Event
import de.matthiasmann.twl.ThemeInfo
import de.matthiasmann.twl.Widget

/**
 *
 * @author Matthias Mann
 */
class InventoryPanel(numSlotsX: Int, numSlotsY: Int) extends Widget {
  private final val slot: Array[ItemSlot] = new Array[ItemSlot](numSlotsX * numSlotsY)
  private var slotSpacing: Int = 0
  private var dragSlot: ItemSlot = null
  private var dropSlot: ItemSlot = null

  val listener: DragListener = new DragListener {
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
  {
    var i: Int = 0
    while (i < slot.length) {
      {
        slot(i) = new ItemSlot
        slot(i).listener = listener
        add(slot(i))
      }
      ({
        i += 1;
        i
      })
    }
  }
  slot(0).item = "red"
  slot(1).item = "green"
  slot(2).item = "blue"
  slot(3).item = "yellow"

  override def getPreferredInnerWidth: Int = {
    return (slot(0).getPreferredWidth + slotSpacing) * numSlotsX - slotSpacing
  }

  override def getPreferredInnerHeight: Int = {
    return (slot(0).getPreferredHeight + slotSpacing) * numSlotsY - slotSpacing
  }

  protected override def layout {
    val slotWidth: Int = slot(0).getPreferredWidth
    val slotHeight: Int = slot(0).getPreferredHeight
    var row: Int = 0
    var y: Int = getInnerY
    var i: Int = 0
    while (row < numSlotsY) {
      {
        {
          var col: Int = 0
          var x: Int = getInnerX
          while (col < numSlotsX) {
            {
              slot(i).adjustSize
              slot(i).setPosition(x, y)
              x += slotWidth + slotSpacing
            }
            ({
              col += 1;
              col
            })
            ({
              i += 1;
              i
            })
          }
        }
        y += slotHeight + slotSpacing
      }
      ({
        row += 1;
        row
      })
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
        dropSlot.setDropState(false, false)
      }
      dropSlot = slot
      if (dropSlot != null) {
        dropSlot.setDropState(true, (dropSlot eq dragSlot ) || dropSlot.canDrop)
      }
    }
  }
}

trait DragListener {
  def dragStarted(slot: ItemSlot, evt: Event)

  def dragging(slot: ItemSlot, evt: Event)

  def dragStopped(slot: ItemSlot, evt: Event)
}


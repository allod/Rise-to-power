package org.risetopower.menu

import de.matthiasmann.twl.Event

trait DragListener {
  def dragStarted(slot: ItemSlot, evt: Event)

  def dragging(slot: ItemSlot, evt: Event)

  def dragStopped(slot: ItemSlot, evt: Event)
}


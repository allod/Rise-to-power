package org.risetopower.menu

import org.newdawn.slick.state.{StateBasedGame, BasicGameState}
import org.risetopower.game.RiseToPowerStateConstants
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.risetopower.BackgroundTest
import de.matthiasmann.twl._
import org.risetopower.twl.BasicTWLGameState
import de.matthiasmann.twl.renderer.{DynamicImage, Image}
import de.matthiasmann.twl.renderer.AnimationState.StateKey

class MainMenuState extends BasicTWLGameState {
 override def getID() = RiseToPowerStateConstants.MAIN_MENU_ID

  val frame = new ResizableFrame();

  override def init(gameContainer: GameContainer, stateBasedGame: StateBasedGame) {
    val fpsCounter = new FPSCounter();

    val inventoryPanel = new InventoryPanel(10, 5);

    frame.setTitle("Inventory");
    frame.setResizableAxis(ResizableFrame.ResizableAxis.NONE);
    frame.add(inventoryPanel);

    rootPane.add(fpsCounter);
    rootPane.add(frame);

    rootPane.validateLayout();
    positionFrame();

  }

  def positionFrame() {
    frame.adjustSize();
    frame.setPosition(
      rootPane.getInnerX() + (rootPane.getInnerWidth() - frame.getWidth())/2,
      rootPane.getInnerY() + (rootPane.getInnerHeight() - frame.getHeight())/2);
  }

 override def update( gameContainer: GameContainer, stateBasedGame: StateBasedGame, i: Int) {
    rootPane.getGUI.update()
 }

 override def render(gameContainer:GameContainer, stateBasedGame:StateBasedGame, graphics:Graphics) {
 }
}

object ItemSlot {
  val STATE_DRAG_ACTIVE = StateKey.get("dragActive");
  val STATE_DROP_OK = StateKey.get("dropOk");
  val STATE_DROP_BLOCKED = StateKey.get("dropBlocked");

}
class ItemSlot extends Widget {

  var item:String = _
  var icon:Image = _
  var listener:DragListener = _
  var dragActive:Boolean = _
  var icons:ParameterMap = _


  def getItem():String= {
    return item;
  }

  def setItem(item:String) {
    this.item = item;
    findIcon();
  }

  def canDrop() :Boolean= {
    item == null
  }

  def getIcon() : Image = {
    icon
  }

  def getListener():DragListener = {
    listener
  }

  def setListener(listener:DragListener) {
    this.listener = listener
  }

  def setDropState(drop:Boolean,  ok:Boolean) {
    val as = getAnimationState()
    as.setAnimationState(ItemSlot.STATE_DROP_OK, drop && ok)
    as.setAnimationState(ItemSlot.STATE_DROP_BLOCKED, drop && !ok)
  }

  override def handleEvent(evt:Event) : Boolean ={
    if(evt.isMouseEventNoWheel()) {
      if(dragActive) {
        if(evt.isMouseDragEnd()) {
          if(listener != null) {
            listener.dragStopped(this, evt)
          }
          dragActive = false
          getAnimationState().setAnimationState(ItemSlot.STATE_DRAG_ACTIVE, false);
        } else if(listener != null) {
          listener.dragging(this, evt)
        }
      } else if(evt.isMouseDragEvent()) {
        dragActive = true
        getAnimationState().setAnimationState(ItemSlot.STATE_DRAG_ACTIVE, true);
        if(listener != null) {
          listener.dragStarted(this, evt)
        }
      }
      return true
    }


    return super.handleEvent(evt)
  }

  override def paintWidget(gui:GUI) {
    if(!dragActive && icon != null) {
      icon.draw(getAnimationState(), getInnerX(), getInnerY(), getInnerWidth(), getInnerHeight())
    }
  }

  override def paintDragOverlay(gui:GUI, mouseX:Int, mouseY:Int, modifier:Int) {
    if(icon != null) {
      val innerWidth = getInnerWidth();
      val innerHeight = getInnerHeight();
      icon.draw(getAnimationState(),
        mouseX - innerWidth/2,
        mouseY - innerHeight/2,
        innerWidth, innerHeight);
    }
  }

  override def applyTheme(themeInfo:ThemeInfo) {
    super.applyTheme(themeInfo)
    icons = themeInfo.getParameterMap("icons")
    findIcon()
  }

  def findIcon() {
    if(item == null || icons == null) {
      icon = null;
    } else {
      icon = icons.getImage(item);
    }
  }
}

trait DragListener {
def dragStarted(slot:ItemSlot, evt:Event)
def dragging(slot:ItemSlot, evt:Event)
def dragStopped(slot:ItemSlot, evt:Event)
}

class InventoryPanel(var numSlotsX:Int, var numSlotsY:Int) extends Widget {

  var slot  = new Array[ItemSlot](numSlotsX * numSlotsY)

  var slotSpacing:Int = _

  var dragSlot:ItemSlot = _
  var dropSlot:ItemSlot = _

  var listener = new DragListener() {
      def dragStarted(slot:ItemSlot, evt:Event) {
        InventoryPanel.this.dragStarted(slot, evt);
      }
      def dragging(slot:ItemSlot, evt:Event) {
        InventoryPanel.this.dragging(slot, evt);
      }
      def dragStopped(slot:ItemSlot, evt:Event) {
        InventoryPanel.this.dragStopped(slot, evt);
      }
    }

    for(i <- 0 until slot.length) {
      slot(i) = new ItemSlot();
      slot(i).setListener(listener);
      add(slot(i));
    }

    slot(0).setItem("red");
    slot(1).setItem("green");
    slot(2).setItem("blue");
    slot(3).setItem("yellow");


  override def getPreferredInnerWidth() : Int = {
    return (slot(0).getPreferredWidth() + slotSpacing)*numSlotsX - slotSpacing;
  }

  override def getPreferredInnerHeight() : Int = {
    return (slot(0).getPreferredHeight() + slotSpacing)*numSlotsY - slotSpacing;
  }

  override def layout() {
    val slotWidth  = slot(0).getPreferredWidth()
    val slotHeight = slot(0).getPreferredHeight()

    var y = getInnerY()

    for(row <- 0 until numSlotsY) {
      var i = 0;
      for(col <- 0 until numSlotsX) {
        i += 1 ;
        var x = getInnerX();
        slot(i).adjustSize();
        slot(i).setPosition(x, y);
        x += slotWidth + slotSpacing;
      }
      y += slotHeight + slotSpacing;
    }
  }

  override def applyTheme(themeInfo:ThemeInfo) {
    super.applyTheme(themeInfo);
    slotSpacing = themeInfo.getParameter("slotSpacing", 5);
  }

  def dragStarted(slot:ItemSlot, evt:Event) {
    if(slot.getItem() != null) {
      dragSlot = slot;
      dragging(slot, evt);
    }
  }

  def dragging(slot:ItemSlot, evt:Event) {
    if(dragSlot != null) {
      val w = getWidgetAt(evt.getMouseX(), evt.getMouseY());
      if(w.isInstanceOf[ItemSlot]) {
        setDropSlot(w.asInstanceOf[ItemSlot])
      } else {
        setDropSlot(null);
      }
    }
  }

  def dragStopped(slot:ItemSlot, evt:Event) {
    if(dragSlot != null) {
      dragging(slot, evt);
      if(dropSlot != null && dropSlot.canDrop() && dropSlot != dragSlot) {
        dropSlot.setItem(dragSlot.getItem());
        dragSlot.setItem(null);
      }
      setDropSlot(null);
      dragSlot = null;
    }
  }

  def setDropSlot(slot:ItemSlot) {
    if(slot != dropSlot) {
      if(dropSlot != null) {
        dropSlot.setDropState(false, false);
      }
      dropSlot = slot;
      if(dropSlot != null) {
        dropSlot.setDropState(true, dropSlot == dragSlot || dropSlot.canDrop());
      }
    }
  }

}

package ID.gui

import scalafx.scene.Node
import scalafx.scene.input.{MouseEvent, ScrollEvent}
import scalafx.Includes._

object EventHelper:

  /**
   * Makes input node Zoomable via mouse scroll
   * @param node the node to make zoomable
   */
  // TODO FIX THIS SHIT
  def makeZoomable(node: Node) =
    val dG = new DragContext
    node.onScroll = (event: ScrollEvent) =>
      val delta = event.getTextDeltaY
      node.scaleX = node.getScaleX + delta/10
      node.scaleY = node.getScaleY + delta/10


   /**
   * Takes a node as a parameter and adds a listener to make it draggable via mouse
   * @param node the node to make draggable
   */
  def makeDraggable(node: Node): Unit =
    val dG = new DragContext
    node.onMousePressed = (event: MouseEvent) =>
      if true then
        dG.anchorX = event.screenX
        dG.anchorY = event.screenY
        dG.initX = node.getTranslateX
        dG.initY = node.getTranslateY
      event.consume()

    node.onMouseDragged = (event: MouseEvent) =>
      if true then
        node.translateX = scala.math.max(0, dG.initX + event.screenX - dG.anchorX)
        node.translateY = scala.math.max(0, dG.initY + event.screenY - dG.anchorY)
      event.consume()


  /**
   * TODO
   * @param node yo idk yet
   */
  def makeScalable(node: Node): Unit = ???


  /**
   * 
   * @return
   */
  def makeSelection = ???


private final class DragContext:
  var initX: Double = 0
  var initY: Double = 0
  var anchorX: Double = 0
  var anchorY: Double = 0
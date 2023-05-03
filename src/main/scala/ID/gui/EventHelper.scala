package ID.gui

import scalafx.scene.Node
import scalafx.scene.input.{MouseDragEvent, MouseEvent, ScrollEvent}
import scalafx.Includes.*
import scalafx.scene.layout.Pane
import scalafx.scene.shape.{Rectangle, Ellipse}
import ID.gui.ObjectNode
import ID.projects.Pos

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
  def makeDraggable(node: Node, condition: => Boolean = true): Unit =
    val dG = new DragContext
    node.onMousePressed = (event: MouseEvent) =>
      if condition then
        dG.anchorX = event.screenX
        dG.anchorY = event.screenY
        dG.initX = node.getTranslateX
        dG.initY = node.getTranslateY
      event.consume()

    node.onMouseDragged = (event: MouseEvent) =>
      if condition then
        node.translateX = scala.math.max(0, dG.initX + event.screenX - dG.anchorX)
        node.translateY = scala.math.max(0, dG.initY + event.screenY - dG.anchorY)
      event.consume()

  /**
   * Highlights a rectangle on mouse drag given the condition parameter is met and outputs an ObjectNode to the pane given as parameter.
   * @param pane IDEditor pane to add the created ObjectNode to
   * @param condition Boolean function to determine if event is executed on action.
   */
  def rectOnDrag(pane: IDEditor, condition: => Boolean) =
    val dG = new DragContext
    val rect = Rectangle(100, 100, scalafx.scene.paint.Color.LightSkyBlue)
    rect.opacity = 0.5
    pane.onMousePressed = (event: MouseEvent) =>
      if condition then
        rect.translateX = event.getX
        rect.translateY = event.getY
        rect.setWidth(0)
        rect.setHeight(0)
        pane.children.add(rect)
    pane.onMouseDragged = (event: MouseEvent) =>
      if condition then
        val newWidth = event.getX - rect.getTranslateX
        val newHeight = event.getY - rect.getTranslateY
        rect.setWidth(newWidth)
        rect.setHeight(newHeight)
    pane.onMouseReleased = (event: MouseEvent) =>
      if condition then
        val x = Rectangle(rect.getWidth, rect.getHeight)
        val pos = Pos(rect.getTranslateX, rect.getTranslateY, 0.0)
        val newNode = ObjectNode(x, pos)
        pane.children.remove(rect)
        pane.children += newNode

  def circOnDrag(pane: IDEditor, condition: => Boolean = true ) =
    val dG = new DragContext
    val circ = Ellipse(0.0, 0.0)
    circ.setFill(scalafx.scene.paint.Color.LightSkyBlue)
    circ.opacity = 0.5
    pane.onMousePressed = (event: MouseEvent) =>
      if condition then
        circ.translateX = event.getX
        circ.translateY = event.getY
        circ.radiusX = 0
        circ.radiusY = 0
        pane.children.add(circ)
    pane.onMouseDragged = (event: MouseEvent) =>
      if condition then
        val xRadius = event.getX - circ.getTranslateX
        val yRadius = event.getY - circ.getTranslateY
        circ.radiusX = xRadius
        circ.radiusY = yRadius
    pane.onMouseReleased = (event: MouseEvent) =>
      if condition then
        val ellipse = Ellipse(circ.getRadiusX, circ.getRadiusY)
        val pos = Pos(circ.getTranslateX, circ.getTranslateY, 0.0)
        val newNode = ObjectNode(ellipse, pos)
        pane.children.remove(circ)
        pane.children += newNode



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
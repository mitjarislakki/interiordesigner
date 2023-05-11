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
    node.addEventHandler(ScrollEvent.Scroll, (event: ScrollEvent) =>
      val delta = event.getTextDeltaY;
      node.scaleX = node.getScaleX + delta/10 ;
      node.scaleY = node.getScaleY + delta/10
    )

   /**
   * Takes a node as a parameter and adds a listener to make it draggable via mouse
   * @param node the node to make draggable
   */
  def makeDraggable(node: ObjectNode, condition: => Boolean = true, comparison: Int => Iterable[ObjectNode]): Unit =
    val dG = new DragContext
    node.addEventHandler(MouseEvent.MousePressed, (event: MouseEvent) =>
      if condition then
        dG.anchorX = event.sceneX ;
        dG.anchorY = event.sceneY ;
        dG.initX = node.getTranslateX ;
        dG.initY = node.getTranslateY ;
      ;
      event.consume()
    )

    node.addEventHandler(MouseEvent.MouseDragged, (event: MouseEvent) =>
      if condition then
        val (x, y) = (node.getTranslateX, node.getTranslateY)
        val (dx, dy) = (event.sceneX - dG.anchorX, event.sceneY - dG.anchorY)
        val (scaleX, scaleY) = (node.getParent.getScaleX, node.getParent.getScaleY)
        val candidateX = x + (dx/scaleX)
        val candidateY = y + (dy/scaleY);
        node.translateX = candidateX;
        node.translateY = candidateY
        val candidate = node.getBoundsInParent
        val compObjects = comparison(node.layer) ++ comparison(0)
        def hasOverlap = compObjects.exists(n2 => (node != n2) && candidate.intersects(n2.getBoundsInParent))
        if hasOverlap then
          node.translateX = x;
          node.translateY = y
        else
          dG.anchorX = event.sceneX;
          dG.anchorY = event.sceneY
      ;
      event.consume()
    )

  /**
   * Highlights a rectangle on mouse drag given the condition parameter is met and outputs an ObjectNode to the pane given as parameter.
   * @param pane IDEditor pane to add the created ObjectNode to
   * @param condition Boolean function to determine if event is executed on action.
   */
  def rectOnDrag(pane: IDEditor, condition: => Boolean) =
    val dG = new DragContext
    val rect = Rectangle(100, 100, scalafx.scene.paint.Color.LightSkyBlue)
    rect.opacity = 0.5
    pane.addEventHandler(MouseEvent.MousePressed, (event: MouseEvent) =>
      if condition then
        rect.translateX = event.getX ;
        rect.translateY = event.getY ;
        rect.setWidth(0) ;
        rect.setHeight(0) ;
        pane.children.add(rect)
    )
    pane.addEventHandler(MouseEvent.MouseDragged, (event: MouseEvent) =>
      if condition then
        val newWidth = event.getX - rect.getTranslateX ;
        val newHeight = event.getY - rect.getTranslateY ;
        rect.setWidth(newWidth) ;
        rect.setHeight(newHeight) ;
    )
    pane.addEventHandler(MouseEvent.MouseReleased, (event: MouseEvent) =>
      if condition then
        val x = Rectangle(rect.getWidth, rect.getHeight) ;
        val pos = Pos(rect.getTranslateX, rect.getTranslateY, 0.0) ;
        val newNode = ObjectNode(x, pos) ;
        pane.children.remove(rect) ;
        pane.children += newNode
    )

  def circOnDrag(pane: IDEditor, condition: => Boolean = true ) =
    val dG = new DragContext
    val circ = Ellipse(100, 100)
    circ.setFill(scalafx.scene.paint.Color.LightSkyBlue)
    circ.opacity = 0.5
    pane.addEventHandler(MouseEvent.MousePressed, (event: MouseEvent) =>
      if condition then
        dG.initX = event.getX;
        dG.initY = event.getY;
        circ.translateX = event.getX ;
        circ.translateY = event.getY ;
        circ.radiusX = 0 ;
        circ.radiusY = 0 ;
        pane.children.add(circ) ;
    )
    pane.addEventHandler(MouseEvent.MouseDragged, (event: MouseEvent) =>
      if condition then
        val xRadius = event.getX - circ.getTranslateX ;
        val yRadius = event.getY - circ.getTranslateY ;
        circ.radiusX = xRadius ;
        circ.radiusY = yRadius
    )
    pane.addEventHandler(MouseEvent.MouseReleased, (event: MouseEvent) =>
      if condition then
        val ellipse = Ellipse(circ.getRadiusX, circ.getRadiusY) ;
        val pos = Pos(2 * circ.getTranslateX - event.getX, 2 * circ.getTranslateY - event.getY, 0.0) ;
        val newNode = ObjectNode(ellipse, pos) ;
        pane.children.remove(circ) ;
        pane.children += newNode ;
    )

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
import ID.gui.IDToolbar.{background, orientation}
import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Point2D}
import scalafx.scene.{Node, Scene}
import scalafx.scene.input.{KeyEvent, MouseEvent}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, CornerRadii, GridPane, HBox, Pane, VBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.shape.{Circle, Cylinder, Ellipse, Rectangle}
import scalafx.Includes.*
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event
import scalafx.scene.control.{Button, Label, TextField, ToolBar}

object graphicTest extends JFXApp3:
  private var selectedNode: Option[Node] = None
  var selection = false
  var draggable: Boolean = true
  var scalable: Boolean = false

  val selectionPane = new Pane()
  val selectionBox = new Rectangle:
    x = 0
    y = 0
    width = 0
    height = 0
    stroke = Blue
    strokeWidth = 1
    fill = LightBlue

  val v = new Pane

  val t = new Rectangle:
      x = 100
      y = 100
      width = 50
      height = 50
      fill = Blue
  val s = new Rectangle:
    x = 200
    y = 250
    width = 20
    height = 40
    fill = Green
  v.children.addAll(t, s)

  override def start() =
    stage = new scalafx.application.JFXApp3.PrimaryStage:
      width = 500
      height = 500
      val root = new GridPane()
      scene = new Scene(parent = root)
      val editor = new BorderPane()
      editor.children.addAll(t, s)
      editor.children.foreach(node => makeDraggable(node))


      root.add(editor, 0, 0)



      val tb = new ToolBar:
        orientation = scalafx.geometry.Orientation.Vertical
        background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))
        val exp1 = new Label(s"Currently selected: ${selectedNode.getOrElse("Nothing")}")
        val field1 = new TextField
        val selBtn = new Button("Select on/off")
        val dragBtn = new Button("Drag on/off")
        selBtn.setOnAction( event =>
          selection = !selection;
          println("set selection to " + selection))

        dragBtn.setOnAction(event =>
          draggable = !draggable
          println("set draggable to " + draggable))
        content ++= List(exp1, field1, selBtn, dragBtn)
        field1.onKeyTyped = (event: KeyEvent) => println("lol")

      root.add(tb, 1, 0)



      // TODO Drag selection
/*
      val selectionContext = new DragContext
      editor.onMouseClicked = (event: MouseEvent) =>
        if selection then
          selectionContext.initX = event.getScreenX
          selectionContext.initY = event.getScreenY
      editor.onMouseDragged = (event: MouseEvent) =>
        if selection then
          selectionBox.translateX = selectionContext.initX
          selectionBox.translateY = selectionContext.initY
          selectionBox.setWidth((event.screenX - selectionContext.initX)/2)
          selectionBox.setHeight((event.screenY - selectionContext.initX)/2)

          println("width: " + selectionBox.getWidth + "\n height: " + selectionBox.getHeight)
      editor.onMouseReleased = (event: MouseEvent) =>
        selectionBox.setTranslateX(0.0)
        selectionBox.setTranslateY(0.0)
        selectionBox.setWidth(0.0)
        selectionBox.setHeight(0.0)
*/
  end start

  def makeDraggable(node: Node): Unit =
      val dG = new DragContext
      node.onMousePressed = (event: MouseEvent) =>
        if draggable then
          dG.anchorX = event.screenX
          dG.anchorY = event.screenY
          dG.initX = node.getTranslateX
          dG.initY = node.getTranslateY
        event.consume()

      node.onMouseDragged = (event: MouseEvent) =>
        if draggable then
          node.translateX = dG.initX + event.screenX - dG.anchorX
          node.translateY = dG.initY + event.screenY - dG.anchorY
        event.consume()
  def makeScalable(node: Node): Unit =
    if scalable then ???
  def makeSelection = ???



private final class DragContext:
  var initX: Double = 0
  var initY: Double = 0
  var anchorX: Double = 0
  var anchorY: Double = 0

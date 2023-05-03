package ID.gui

import javafx.geometry.VerticalDirection
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.paint.Color.*
import scalafx.scene.{Node, Scene}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent, ScrollEvent}
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, SeparatorMenuItem, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, ColumnConstraints, CornerRadii, GridPane, Pane, RowConstraints}
import scalafx.scene.text.Font
import ID.projects.Project
import ID.files.IDReader
import scalafx.Includes.*
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.event
import scalafx.event.ActionEvent
import scalafx.scene.shape.Rectangle

import scala.util.Random

object IDGUI extends scalafx.application.JFXApp3.PrimaryStage:
  val editor = tempEditor
  width = 800
  height = 500


  val primaryMonitor = java.awt.Toolkit.getDefaultToolkit.getScreenSize
  title = "Interior Designer"
  val root = GridPane()
  root.gridLinesVisible = true
  this.scene = new Scene(parent = root)

  val test = new Pane()

  root.add(test, 1, 1)
  root.add(IDMenu.menuBar, 0, 0, 3, 1)
  root.add(IDToolbar, 0, 1)
  root.add(editor, 1, 1)
  root.add(IDOProperties, 2, 1)
  editor.autosize()

  // listener for new nodes in children of editor
  editor.objects.onChange { (obs, chs) =>
    for change <- chs do
      change match
        case ObservableBuffer.Add(_, list) =>
          list.foreach(node =>
            println("Node added")
            makeSelectable(node);
            EventHelper.makeDraggable(node)
          )
        case ObservableBuffer.Remove(_, node: ObjectNode) => println("Node deleted")
        case _ =>
  }

  // file chooser for "open project" -button in menu
  var project: Option[Project] = None
  IDMenu.openProject.onAction = (event) =>
     val t = new scalafx.stage.FileChooser()
     t.setTitle("Testing")
     val file = t.showOpenDialog(this)
     if file != null then
       if (file.getName.endsWith(".YAML")) then
         project = Some(IDReader(file).readFile())
       else println("NOT NICE")

  // event handlers for ObjectNodes in editor
  def makeSelectable(node: javafx.scene.Node) =
    node match
      case a: ObjectNode =>
        // select node on click if SEL tool in use
        a.onMouseClicked = (event: MouseEvent) => if IDToolbar.select.isSelected then editor.selectedNode.value = a
        // refresh IDOProperties on node drag if SEL tool in use
        a.addEventHandler(MouseEvent.MouseDragged, (event: MouseEvent) => if IDToolbar.select.isSelected then Option(editor.selectedNode.value).foreach(n => IDOProperties.update(n)))
      case _ =>

  // add rectangles
  EventHelper.rectOnDrag(editor, IDToolbar.addRectangle.isSelected)

  // add ellipses
  EventHelper.circOnDrag(editor, IDToolbar.addCircle.isSelected)

  val rect = Rectangle(10, 10, scalafx.scene.paint.Color.Green)
  val newNode = ObjectNode(rect, ID.projects.Pos())
  //editor.children += newNode

  // updates property values on node change
  val drawRect =
  editor.selectedNode.onChange((_, _, newNode) =>
    IDOProperties.update(newNode);
  )

  //
  editor.onKeyPressed = (event: KeyEvent) => event.code match
    case KeyCode.BackSpace =>
      println("yeah pressed")
      Option(editor.selectedNode.value).foreach(node => editor.children.remove(node))
    case _ =>

  // listeners for tools/buttons
  IDToolbar.addRectangle.onAction = (event: ActionEvent) =>

  // listeners for IDOProperties input
  // name
  IDOProperties.nameBox.onKeyTyped = (event: KeyEvent) =>
    Option(editor.selectedNode.value).foreach(node => node.setName(IDOProperties.nameBox.getText))
  // X pos
  IDOProperties.xBox.onKeyTyped = (event: KeyEvent) =>
      Option(editor.selectedNode.value).foreach(node => IDOProperties.xBox.getText.toDoubleOption.foreach(node.setTranslateX(_)))
  // Y pos
  IDOProperties.yBox.onKeyTyped = (event: KeyEvent) =>
      Option(editor.selectedNode.value).foreach(node => IDOProperties.yBox.getText.toDoubleOption.foreach(node.setTranslateY(_)))
  // width
  IDOProperties.wBox.onKeyTyped = (event: KeyEvent) =>
    Option(editor.selectedNode.value).foreach(node =>
      IDOProperties.wBox.getText.toDoubleOption match
        case Some(n) if n>0 =>
          node.widthTo(n)
        case _ =>
      )
  // height
  IDOProperties.hBox.onKeyTyped = (event: KeyEvent) =>
    Option(editor.selectedNode.value).foreach(node =>
      IDOProperties.hBox.getText.toDoubleOption match
        case Some(n) if n>0 =>
          node.heightTo(n)
        case _ =>
      )
  // layer
  IDOProperties.layerBox.onKeyTyped = (event: KeyEvent) =>
    Option(editor.selectedNode.value).foreach(node =>
      IDOProperties.layerBox.getText.toIntOption match
        case Some(n) if n>=0 => node.setLayer(n)
        case _ =>
      )
  // color
  IDOProperties.colorPicker.onAction = (event: ActionEvent) =>
    Option(editor.selectedNode.value).foreach(node => node.setColor(IDOProperties.colorPicker.getValue))
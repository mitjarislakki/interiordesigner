package ID.gui

import javafx.geometry.VerticalDirection
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.paint.Color.*
import scalafx.scene.{Node, Scene}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent, ScrollEvent}
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, SeparatorMenuItem, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, ColumnConstraints, CornerRadii, GridPane, Pane, Priority, RowConstraints}
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

  width = 800
  height = 500


  val primaryMonitor = java.awt.Toolkit.getDefaultToolkit.getScreenSize
  title = "Interior Designer"
  val root = GridPane()
  root.gridLinesVisible = true
  this.scene = new Scene(parent = root)


  val outerPane = ZoomableScrollPane
  setEditor(tempEditor)
  val oNView = ONList
  root.add(IDMenu.menuBar, 0, 0, 3, 1)
  root.add(IDToolbar, 0, 1, 1, 2)
  root.add(outerPane, 1, 1, 1, 2)
  root.add(IDOProperties, 2, 1, 1, 1)
  root.add(oNView, 2, 2)
  root.autosize()

  // helper functions to create fast column & row constraints
  outerPane.hgrow = Priority.Always
  outerPane.vgrow = Priority.Always
  oNView.vgrow = Priority.Always
  IDToolbar.vgrow = Priority.Always


  // file chooser for "open project" -button in menu
  var project: Option[Project] = None
  IDMenu.openProject.onAction = (event) =>
     val t = new scalafx.stage.FileChooser()
     t.setTitle("Testing")
     val file = t.showOpenDialog(this)
     if file != null then
       if (file.getName.endsWith(".YAML")) then
         project = Some(IDReader.readProject(file))
       else println("NOT NICE")

  def setEditor(editor: IDEditor) =
    outerPane.setChild(editor)
    ONList.setInput(editor.children)
    // listener for new nodes in children of editor
    editor.objects.onChange { (obs, chs) =>
      for change <- chs do
        change match
          case ObservableBuffer.Add(_, list) =>
            list.foreach(node => node match
              case v: ObjectNode =>
                v.initialize();
                makeSelectable(v);
                EventHelper.makeDraggable(v, IDToolbar.select.isSelected, editor.objectsAtLayer);
                v.del.onAction = e => editor.children.remove(v)
              case _ =>
            )
          case ObservableBuffer.Remove(_, list) => list.foreach(node => node match
            case v: ObjectNode =>
            case _ =>
          )
          case _ =>
    }


    // event handlers for ObjectNodes in editor
    def makeSelectable(node: javafx.scene.Node) =
      node match
        case a: ObjectNode =>
          // select node on click if SEL tool in use
          a.onMousePressed = (event: MouseEvent) => if IDToolbar.select.isSelected then editor.selectedNode.value = a
          // refresh IDOProperties on node drag if SEL tool in use
          a.addEventHandler(MouseEvent.MouseDragged, (event: MouseEvent) => if IDToolbar.select.isSelected then Option(editor.selectedNode.value).foreach(n => IDOProperties.update(n)))
        case _ =>

    // add rectangles
    EventHelper.rectOnDrag(editor, IDToolbar.addRectangle.isSelected)

    // add ellipses
    EventHelper.circOnDrag(editor, IDToolbar.addCircle.isSelected)

    // switch to Object Node editor
    IDToolbar.oNEdit.onAction = (event: ActionEvent) => println("nice")

    // updates property values on node change
    editor.selectedNode.onChange((_, _, newNode) =>
      IDOProperties.update(newNode);
    )



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
    // length
    IDOProperties.lBox.onKeyTyped = (event: KeyEvent) =>
      Option(editor.selectedNode.value).foreach(node =>
        IDOProperties.lBox.getText.toDoubleOption match
          case Some(n) if n>0 =>
            node.lengthTo(n)
          case _ =>
        )
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
          case Some(n) if n>0 => node.setVHeight(n)
          case _ =>
      )
    //rotation
    IDOProperties.rBox.onKeyTyped = (event: KeyEvent) =>
      Option(editor.selectedNode.value).foreach(node =>
        IDOProperties.rBox.getText.toDoubleOption.foreach(n => node.rotate = n)
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
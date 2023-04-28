package ID.gui

import javafx.geometry.VerticalDirection
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.paint.Color.*
import scalafx.scene.{Node, Scene}
import scalafx.scene.input.{KeyEvent, MouseEvent, ScrollEvent}
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, SeparatorMenuItem, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, ColumnConstraints, CornerRadii, GridPane, Pane, RowConstraints}
import scalafx.scene.text.Font
import ID.projects.Project
import ID.files.IDReader
import scalafx.Includes.*
import scalafx.beans.property.ObjectProperty
import scalafx.scene.input.MouseEvent
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

  // changes selected node on click
  editor.objects.foreach(node =>
    node match
      case a: ObjectNode => a.onMouseClicked = (event: MouseEvent) => editor.selectedNode.value = a
      case _ =>
  )

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
  // width
  IDOProperties.wBox.onKeyTyped = (event: KeyEvent) =>
    Option(editor.selectedNode.value).foreach(node =>
      IDOProperties.wBox.getText.toDoubleOption match
        case Some(n) if n>0 =>
          val scaleFactor: Double = n / ((node.getBoundsInParent.getWidth)/node.getScaleX);
          node.scaleX = scaleFactor
        case _ =>
      )
  // height
  IDOProperties.hBox.onKeyTyped = (event: KeyEvent) =>
    Option(editor.selectedNode.value).foreach(node =>
      IDOProperties.hBox.getText.toDoubleOption match
        case Some(n) if n>0 =>
          val scaleFactor: Double = n / ((node.getBoundsInParent.getHeight)/node.getScaleY);
          node.scaleY = scaleFactor
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
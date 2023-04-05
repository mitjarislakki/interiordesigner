package ID.gui

import javafx.geometry.VerticalDirection
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.paint.Color.*
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, SeparatorMenuItem, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, ColumnConstraints, CornerRadii, GridPane, Pane, RowConstraints}
import scalafx.scene.text.Font
import ID.projects.Project
import ID.files.IDReader
import scalafx.scene.input.MouseEvent
import scalafx.scene.shape.Rectangle

import scala.util.Random

object IDGUI extends scalafx.application.JFXApp3.PrimaryStage:
   width = 500
   height = 500



   val primaryMonitor = java.awt.Toolkit.getDefaultToolkit.getScreenSize
   title = "Interior Designer"
   val root = GridPane()
   this.scene = new Scene(parent = root)

   val test = new Pane()

   val column0 = new ColumnConstraints:
     percentWidth = 16
   val column1 = new ColumnConstraints:
     percentWidth = 68
   val column2 = new ColumnConstraints:
     percentWidth = 16

   root.columnConstraints = Array[ColumnConstraints](column0, column1, column2)

   root.add(test, 1, 1)
   root.add(IDMenu.menuBar, 0, 0, 3, 1)
   root.add(IDToolbar, 0, 1)
   root.add(IDOProperties, 2, 1)
/*
    // Event handler for the "open project" menu button. Opens a file chooser.
   IDMenu.openProject.onAction = (event) =>
     val t = new scalafx.stage.FileChooser()
     t.setTitle("Testing")
     val file = t.showOpenDialog(this)
     if file != null then
       if (file.getName.endsWith(".YAML")) then
         project = Some(IDReader(file).readFile())
       else println("NOT NICE")

 IDToolbar.temp.onAction = (event) =>
   println("Starting draw")
   val d = IDCanvas.g
   project.foreach(project =>
     project.objects.foreach(IDO =>
       IDO.shapes.foreach(shape =>
         d.fill = shape.color;
         shape match
           case a: Rectangle => d.fillRect(a.pos.x, a.pos.y, a.width, a.length)
           case b: Oval => d.fillOval(b.pos.x, b.pos.y, b.width, b.length)
           case c: Triangle => d.fillPolygon(Array[Double](c.pos.x, c.pos.x,c.pos.x+c.width),Array[Double](c.pos.y, c.pos.y,c.pos.y+c.length), 3 )
           case _ => println("Unknown shape")
       )
     )
   )
*/

package ID.gui

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.paint.Color.*
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Label, Menu, MenuBar, MenuItem, SeparatorMenuItem, ToolBar}
import scalafx.scene.layout.{BorderPane, GridPane}

object DesignerGUI extends scalafx.application.JFXApp3.PrimaryStage:
 val primaryMonitor = java.awt.Toolkit.getDefaultToolkit.getScreenSize
 title = "Hello World!"
 val root = GridPane()
 val _scene = Scene(parent = root)
 this.scene = _scene
 /*scene = new Scene(400, 50) {
  val menuBar = DesignerMenu.menuBar
  menuBar.prefWidth = this.getWidth
  content = List(menuBar)
  }*/
 val projectCanvas = Canvas(1920, 1080)
 val g = projectCanvas.graphicsContext2D
 g.fill = Gray
 g.fillRect(0, 0, projectCanvas.getWidth, projectCanvas.getHeight)

 val tools = new ToolBar

 val properties = new ToolBar

 root.add(projectCanvas, 1, 1)
 root.add(DesignerMenu.menuBar, 0, 0, 3, 1)
 root.add(tools, 0, 1)
 root.add(properties, 2, 1)

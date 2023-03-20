package ID.gui

import javafx.geometry.VerticalDirection
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.paint.Color.*
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, SeparatorMenuItem, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, ColumnConstraints, CornerRadii, GridPane, RowConstraints}
import scalafx.scene.text.Font

object DesignerGUI extends scalafx.application.JFXApp3.PrimaryStage:
 val primaryMonitor = java.awt.Toolkit.getDefaultToolkit.getScreenSize
 title = "Hello World!"
 val root = GridPane()
 val _scene = new Scene(parent = root)
 this.scene = _scene

 val projectCanvas = Canvas(1920, 1080)
 val g = projectCanvas.graphicsContext2D
 g.fill = White
 g.fillRect(0, 0, projectCanvas.getWidth, projectCanvas.getHeight)

 val tools = new ToolBar {
  orientation=scalafx.geometry.Orientation.Vertical
  background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))
 }
 val select = new Button {text = "Select"}
 val addRectangle = new Button {text = "Rectangle tool"}
 val addCircle = new Button {text = "Circle tool"}
 val addTriangle = new Button{text = "Triangle tool"}

 tools.content ++= List(select, addRectangle, addCircle, addTriangle)

 val properties = new ToolBar {
  orientation=scalafx.geometry.Orientation.Vertical
  background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))}
 properties.content ++= List(new Label{text ="Lorem ipsum"; textFill = White;font = Font.font(36)})


 root.add(projectCanvas, 1, 1)
 root.add(DesignerMenu.menuBar, 0, 0, 3, 1)
 root.add(tools, 0, 1)
 root.add(properties, 2, 1)

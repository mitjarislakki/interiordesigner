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

object IDGUI extends scalafx.application.JFXApp3.PrimaryStage:
 val primaryMonitor = java.awt.Toolkit.getDefaultToolkit.getScreenSize
 title = "Interior Designer"
 val root = GridPane()
 this.scene = new Scene(parent = root)

 val projectCanvas = Canvas(primaryMonitor.getWidth-400, primaryMonitor.getHeight-400)
 val g = projectCanvas.graphicsContext2D
 g.fill = White
 g.fillRect(0, 0, projectCanvas.getWidth, projectCanvas.getHeight)

 val tools = IDToolbar

 val properties = new ToolBar {
  orientation=scalafx.geometry.Orientation.Vertical
  background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))}
 properties.content ++= List(new Label{text ="Lorem ipsum"; textFill = White;font = Font.font(36)})


 root.add(projectCanvas, 1, 1)
 root.add(IDMenu.menuBar, 0, 0, 3, 1)
 root.add(tools, 0, 1)
 root.add(properties, 2, 1)

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


 root.add(IDCanvas, 1, 1)
 root.add(IDMenu.menuBar, 0, 0, 3, 1)
 root.add(IDToolbar, 0, 1)
 root.add(IDOProperties, 2, 1)


package ID.gui

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Label, Menu, MenuBar, MenuItem, SeparatorMenuItem}
import scalafx.scene.layout.BorderPane

object DesignerGUI extends scalafx.application.JFXApp3.PrimaryStage:
 title = "Hello"
 scene = new Scene(400, 200) {
  val menuBar = DesignerMenu.menuBar
  menuBar.prefWidth = this.getWidth
  content = List(menuBar)
  }
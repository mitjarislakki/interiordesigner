package ID.gui

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane

object DesignerGUI extends scalafx.application.JFXApp3.PrimaryStage:
 title = "Hello"
 scene = new Scene {
  root = new BorderPane {
   padding = Insets(75)
   center = new Label("Hello World")
  }
 }
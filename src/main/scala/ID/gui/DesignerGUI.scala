package ID.gui

import ID.gui.DesignerGUI.{background, contents, preferredSize, title}

import scala.swing.*

object DesignerGUI extends MainFrame:
 title = "Interior Designer"

 this.resizable = true

 preferredSize = new Dimension(1920, 1080)

 contents = new BoxPanel(Orientation.Horizontal){
  background = java.awt.Color.DARK_GRAY}

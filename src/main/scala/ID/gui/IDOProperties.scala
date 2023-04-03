package ID.gui

import scalafx.scene.control.{Label, ToolBar}
import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font

object IDOProperties extends ToolBar:
    orientation=scalafx.geometry.Orientation.Vertical
    background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))
    content ++= List(new Label{text ="Lorem ipsum"; textFill = White;font = Font.font(36)})
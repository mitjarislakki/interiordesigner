package ID.gui

import scalafx.geometry.Insets
import scalafx.scene.control.{Button, ToggleButton, ToggleGroup, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Color.DarkSlateGray

object IDToolbar extends ToolBar:
    orientation =scalafx.geometry.Orientation.Vertical
    background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))
    val select = new ToggleButton {text = "SEL"}
    val addRectangle = new ToggleButton {text = "REC"}
    val addCircle = new ToggleButton {text = "CIRC"}
    val addTriangle = new ToggleButton{text = "TRI"}
    val oNEdit = new ToggleButton{text = "EDIT"}

    val group = new ToggleGroup
    group.toggles = List(select, addRectangle, addCircle, addTriangle, oNEdit)
    content ++= List(select, addRectangle, addCircle, addTriangle, oNEdit)

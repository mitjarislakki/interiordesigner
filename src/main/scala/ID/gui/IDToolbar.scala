package ID.gui

import scalafx.geometry.Insets
import scalafx.scene.control.{Button, ToggleButton, ToggleGroup, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Color.DarkSlateGray

object IDToolbar extends ToolBar:
    orientation =scalafx.geometry.Orientation.Vertical
    background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))
    val select = new ToggleButton {text = "SEL"}
    val move = new ToggleButton{text = "MOVE"}
    val addRectangle = new ToggleButton {text = "REC"}
    val addCircle = new ToggleButton {text = "CIRC"}
    val addTriangle = new ToggleButton{text = "TRI"}
    val oNEdit = new ToggleButton{text = "EDIT"}

    private val group = new ToggleGroup
    group.toggles = List(select, move, addRectangle, addCircle)
    content ++= List(select, move, addRectangle, addCircle)

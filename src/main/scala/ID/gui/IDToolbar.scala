package ID.gui

import scalafx.geometry.Insets
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Color.DarkSlateGray

object IDToolbar extends ToolBar:
    orientation =scalafx.geometry.Orientation.Vertical
    background = Background(Array(new BackgroundFill((DarkSlateGray), CornerRadii.Empty, Insets.Empty)))
    val select = new Button {text = "Select"}
    val addRectangle = new Button {text = "Rectangle tool"}
    val addCircle = new Button {text = "Circle tool"}
    val addTriangle = new Button{text = "Triangle tool"}
    val temp = new Button{text = "TEMP DRAW PROJECT"}

    content ++= List(select, addRectangle, addCircle, addTriangle, temp)

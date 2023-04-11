package ID.gui

import scalafx.scene.control.{Label, TextField}
import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, GridPane}
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font

object IDOProperties extends GridPane():
      this.setPadding(scalafx.geometry.Insets(10, 10, 10, 10))
      this.setVgap(5)
      this.setHgap(5)
      val xLabel = Label(s"X: ")
      val yLabel = Label("Y: ")
      val xBox = TextField()
      xBox.setPromptText("0")
      val yBox = TextField()
      yBox.setPromptText("0")
      val wLabel = Label("Width: ")
      val hLabel = Label("Height: ")
      val wBox = TextField()
      wBox.setPromptText("0")
      val hBox = TextField()
      hBox.setPromptText("0")
      val scaleXBox = TextField()
      scaleXBox.setPromptText("0")
      val scaleYBox = TextField()
      scaleYBox.setPromptText("0")

      this.add(xLabel, 0, 0)
      this.add(xBox, 1,  0)
      this.add(yLabel, 0, 1)
      this.add(yBox, 1, 1)
      this.add(wLabel, 0, 2)
      this.add(wBox, 1, 2)
      this.add(hLabel, 0, 3)
      this.add(hBox, 1, 3)
      this.add(scaleXBox, 1, 4)
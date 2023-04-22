package ID.gui

import scalafx.scene.control.{Label, TextField}
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, GridPane}
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font

object IDOProperties extends GridPane():
      padding = scalafx.geometry.Insets(10, 10, 10, 10)
      vgap = 5
      hgap = 5

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
      val sXLabel = Label("X-Scale: ")
      val scaleXBox = TextField()
      scaleXBox.setPromptText("0")
      val sYLabel = Label("Y-Scale: ")
      val scaleYBox = TextField()
      scaleYBox.setPromptText("0")

      def update(node: Node) =
            xBox.setText(node.getTranslateX.toString)
            yBox.setText(node.getTranslateY.toString)
            wBox.setText(node.getBoundsInParent.getWidth.toString)
            hBox.setText(node.getBoundsInParent.getHeight.toString)
            scaleXBox.setText(node.getScaleX.toString)
            scaleYBox.setText(node.getScaleY.toString)

      this.add(xLabel, 0, 0)
      this.add(xBox, 1,  0)
      this.add(yLabel, 0, 1)
      this.add(yBox, 1, 1)
      this.add(wLabel, 0, 2)
      this.add(wBox, 1, 2)
      this.add(hLabel, 0, 3)
      this.add(hBox, 1, 3)
      add(sXLabel, 0, 4)
      this.add(scaleXBox, 1, 4)
      add(sYLabel, 0, 5)
      add(scaleYBox, 1, 5)
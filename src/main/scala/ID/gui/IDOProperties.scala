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

      val name = Label("Name: ")
      val nameBox = TextField()

      val xLabel = Label("X: ")
      val xBox = TextField()
      xBox.setPromptText("0")
      
      val yLabel = Label("Y: ")
      val yBox = TextField()
      yBox.setPromptText("0")
      
      val wLabel = Label("Width: ")
      val wBox = TextField()
      wBox.setPromptText("0")
      
      val hLabel = Label("Height: ")
      val hBox = TextField()
      hBox.setPromptText("0")
      
      val sXLabel = Label("X-Scale: ")
      val scaleXBox = TextField()
      scaleXBox.setPromptText("0")
      
      val sYLabel = Label("Y-Scale: ")
      val scaleYBox = TextField()
      scaleYBox.setPromptText("0")
      
      val layerLabel = Label("Layer: ")
      val layerBox = TextField()
      layerBox.setPromptText("0")

      def update(node: ObjectNode) =
            nameBox.setText(node.name)
            xBox.setText(node.getTranslateX.toString)
            yBox.setText(node.getTranslateY.toString)
            wBox.setText(node.getBoundsInParent.getWidth.toString)
            hBox.setText(node.getBoundsInParent.getHeight.toString)
            scaleXBox.setText(node.getScaleX.toString)
            scaleYBox.setText(node.getScaleY.toString)

      add(name, 0, 0)
      add(nameBox, 1, 0)
      add(xLabel, 0, 1)
      add(xBox, 1,  1)
      add(yLabel, 0, 2)
      add(yBox, 1, 2)
      add(wLabel, 0, 3)
      add(wBox, 1, 3)
      add(hLabel, 0, 4)
      add(hBox, 1, 4)
      add(sXLabel, 0, 5)
      add(scaleXBox, 1, 5)
      add(sYLabel, 0, 6)
      add(scaleYBox, 1, 6)
      add(layerLabel, 0, 7)
      add(layerBox, 1, 7)
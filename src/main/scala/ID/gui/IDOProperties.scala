package ID.gui

import scalafx.scene.control.{ColorPicker, Label, TextField}
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, GridPane}
import scalafx.scene.paint.Color
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
      
      val lengthLabel = Label("Length: ")
      val lBox = TextField()
      lBox.setPromptText("0")

      val widthLabel = Label("Width: ")
      val wBox = TextField()
      wBox.setPromptText("0")

      val heightLabel = Label("Height: ")
      val hBox = TextField()
      hBox.setPromptText("0")

      val rotationLabel = Label("Y-Scale: ")
      val rBox = TextField()
      rBox.setPromptText("0")

      val layerLabel = Label("Layer: ")
      val layerBox = TextField()
      layerBox.setPromptText("0")

      val colorLabel = Label("Color: ")
      val colorPicker = new ColorPicker

      def update(node: ObjectNode) =
            nameBox.setText(node.name)
            xBox.setText(node.getTranslateX.toString)
            yBox.setText(node.getTranslateY.toString)
            lBox.setText(node.getBoundsInParent.getWidth.toString)
            wBox.setText(node.getBoundsInParent.getHeight.toString)
            hBox.setText(node.vHeight.toString)
            rBox.setText(node.getRotate.toString)
            layerBox.setText(node.layer.toString)
            node.main.foreach(shape => colorPicker.value = shape.getFill.asInstanceOf[javafx.scene.paint.Color])

      add(name, 0, 0)
      add(nameBox, 1, 0)
      add(xLabel, 0, 1)
      add(xBox, 1,  1)
      add(yLabel, 0, 2)
      add(yBox, 1, 2)
      add(lengthLabel, 0, 3)
      add(lBox, 1, 3)
      add(widthLabel, 0, 4)
      add(wBox, 1, 4)
      add(heightLabel, 0, 5)
      add(hBox, 1, 5)
      add(rotationLabel, 0, 6)
      add(rBox, 1, 6)
      add(layerLabel, 0, 7)
      add(layerBox, 1, 7)
      add(colorLabel, 0, 8)
      add(colorPicker, 1, 8)
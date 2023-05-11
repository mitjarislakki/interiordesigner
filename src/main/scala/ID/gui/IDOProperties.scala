package ID.gui

import scalafx.scene.control.{ColorPicker, Label, TextField}
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.layout.Priority.Always
import scalafx.scene.layout.{Background, BackgroundFill, ColumnConstraints, CornerRadii, GridPane}
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
            node.baseShape.foreach(shape => colorPicker.value = shape.getFill.asInstanceOf[javafx.scene.paint.Color])
      val contents = List(name, nameBox, xLabel, xBox, yLabel, yBox, lengthLabel, lBox, widthLabel, wBox, heightLabel, hBox, rotationLabel, rBox, layerLabel, layerBox, colorLabel, colorPicker)
      // loop to initialize contents
      var contentIndex = 0
      for i <- 0 until contents.size/2 do
            for j <- 0 to 1 do
                  add(contents(contentIndex), j, i)
                  contentIndex += 1

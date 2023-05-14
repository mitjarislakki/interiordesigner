package ID.gui

import scalafx.scene.control.{ColorPicker, Label, TextField}
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.layout.Priority.Always
import scalafx.scene.layout.{Background, BackgroundFill, ColumnConstraints, CornerRadii, GridPane, Priority, Region}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font

object IDOProperties extends GridPane():
      maxWidth = Region.USE_PREF_SIZE
      padding = scalafx.geometry.Insets(10, 10, 10, 10)
      vgap = 5
      hgap = 5

      private val name = Label("Name: ")
      val nameBox = TextField()

      private val xLabel = Label("X: ")
      val xBox = TextField()
      xBox.setPromptText("0")
      
      private val yLabel = Label("Y: ")
      val yBox = TextField()
      yBox.setPromptText("0")
      
      private val lengthLabel = Label("Length: ")
      val lBox = TextField()
      lBox.setPromptText("0")

      private val widthLabel = Label("Width: ")
      val wBox = TextField()
      wBox.setPromptText("0")

      private val heightLabel = Label("Height: ")
      val hBox = TextField()
      hBox.setPromptText("0")

      private val rotationLabel = Label("Rotation: ")
      val rBox = TextField()
      rBox.setPromptText("0")

      private val layerLabel = Label("Layer: ")
      val layerBox = TextField()
      layerBox.setPromptText("0")

      private val colorLabel = Label("Color: ")
      val colorPicker = new ColorPicker

      def update(node: ObjectNode) =
            nameBox.setText(node.name)
            xBox.setText(EventHelper.roundToDecimalString(node.getTranslateX))
            yBox.setText(EventHelper.roundToDecimalString(node.getTranslateY))
            lBox.setText(node.getWidth.toString)
            wBox.setText(node.getHeight.toString)
            hBox.setText(node.vHeight.toString)
            rBox.setText(node.getRotate.toString)
            layerBox.setText(node.getLayer.toString)
            node.baseShape.foreach(shape => colorPicker.value = shape.getFill.asInstanceOf[javafx.scene.paint.Color])
      private val contents = List(name, nameBox, xLabel, xBox, yLabel, yBox, lengthLabel, lBox, widthLabel, wBox, heightLabel, hBox, rotationLabel, rBox, layerLabel, layerBox, colorLabel, colorPicker)
      // loop to initialize contents
      private var contentIndex = 0
      for i <- 0 until contents.size/2 do
            for j <- 0 to 1 do
                  val node = contents(contentIndex)
                  node.minWidth = Region.USE_PREF_SIZE
                  node.minHeight = Region.USE_PREF_SIZE
                  add(contents(contentIndex), j, i)
                  contentIndex += 1

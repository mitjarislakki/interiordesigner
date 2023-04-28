package ID.gui
import ID.projects.Pos
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.shape.Shape

import scala.collection.mutable.Buffer

class ObjectNode(_name: String, val shapes: Buffer[(Shape, Pos)], private var _layer: Int = 0) extends javafx.scene.layout.StackPane:
  private val nameLabel = Label(_name)

  def setLayer(n: Int) =
    if n >= 0 then _layer = n

  def name = nameLabel.getText

  def changeName(input: String) = nameLabel.setText(input)

  val main = shapes.headOption.map(_._1)
  main.foreach(shape => getChildren.addAll(shape, nameLabel))
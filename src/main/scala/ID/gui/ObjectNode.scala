package ID.gui
import ID.projects.Pos
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.shape.Shape

import scala.collection.mutable.Buffer

class ObjectNode(private var _name: String, val shapes: Buffer[(Shape, Pos)]) extends StackPane:
  def name = _name
  val main = shapes.headOption.map(_._1)
  main.foreach(shape => children.addAll(shape, Label(name)))
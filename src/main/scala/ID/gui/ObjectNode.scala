package ID.gui
import ID.projects.Pos
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Shape

import scala.collection.mutable.Buffer

class ObjectNode(private var name: String, val shapes: Buffer[(Shape, Pos)]) extends Pane:
  val main = shapes.headOption
  main.foreach(master =>

    shapes.tail.foreach((s: Shape, p: Pos) =>
      s.translateX = p.x;
      s.translateY = p.y))
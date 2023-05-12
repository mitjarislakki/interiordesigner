package ID.files

import scala.collection.mutable.Buffer
import ID.gui.ObjectNode
import scalafx.scene.shape.{Shape, Rectangle, Ellipse}
import ID.projects.Pos
import scalafx.scene.paint.Color

class ONContender:
  var label: Option[String] = None
  var layer: Option[Int] = None
  val objects = Buffer[String]()
  def objectsToShapePos: Seq[(Shape, Pos)] =
    val attributeList = objects.grouped(7)
    attributeList.map( n =>
      val length = n(1).toDouble
      val width = n(2).toDouble
      val height = n(3).toDouble
      val rotation = n(4).toDouble
      val color = scalafx.scene.paint.Color.web(n(5))
      val position = n(6).split(',')map(_.trim.toDouble)
      val pos = Pos(position(0), position(1), position(2))
      val shape = n.head match
        case "rectangle" =>
          Rectangle(length, width, color);
        case "ellipse" =>
          Ellipse(length, width);
        case _ => throw Error("Unrecognized shape")
      ;
      shape.rotate = rotation;
      shape.setFill(color)
      (shape, pos)).toSeq
  def isValid = label.isDefined && layer.exists(n => n > -1)
  def composeObjectNode  =
    val int = layer.getOrElse(throw Error(s"Incorrect layer in $label"))
    val name: String = label.getOrElse(throw Error("Label undefined"))
    (name, objectsToShapePos, int)

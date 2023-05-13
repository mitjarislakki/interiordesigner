package ID.files

import scala.collection.mutable.Buffer
import ID.gui.ObjectNode
import scalafx.scene.shape.{Shape, Rectangle, Ellipse}
import ID.projects.Pos
import scalafx.scene.paint.Color

class ONContender:
  var startingLine: Int = 0
  var label: Option[String] = None
  var layer: Option[Int] = None
  val objects = Buffer[String]()
  private var nodes: Option[Seq[(Shape, Pos)]] = None
  def defineObjectNodeParams(): Boolean =
    try
      val attributeList = objects.grouped(7)
      val shapePosList: Seq[(Shape, Pos)] = attributeList.map( n =>
        val length = n(1).toDouble
        val width = n(2).toDouble
        val height = n(3).toDouble
        val rotation = n(4).toDouble
        val color = scalafx.scene.paint.Color.web(n(5))
        val position = n(6).split(',').map(_.trim.toDouble)
        val pos = Pos(position(0), position(1), position(2))
        val shape = n.head match
          case "rectangle" =>
            Rectangle(length, width, color);
          case "ellipse" =>
            Ellipse(length, width);
          case _ => throw UnrecognizedShapeError("Unrecognized shape")
        ;
        shape.rotate = rotation;
        shape.setFill(color)
        (shape, pos)).toSeq
      nodes = Some(shapePosList)
      true
    catch
      case e: NumberFormatException => false
      case g: IllegalArgumentException => false
      case h: IndexOutOfBoundsException => false
      case f: NoSuchElementException => false
  def getNodeParams: Option[(String, Seq[(Shape, Pos)], Int)] =
    label.flatMap(n => layer.flatMap(l => nodes.map(s => (n, s, l))))

  def isValid = label.isDefined && layer.isDefined && defineObjectNodeParams()

  override def toString = s"Object labeled ${label.getOrElse("[Undefined]")} at layer ${layer.getOrElse("[Undefined])}")} with objects ${nodes.getOrElse("[Undefined]")} has missing parameters."

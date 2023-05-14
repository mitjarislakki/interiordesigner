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
  var rotation: Option[Double] = None
  var height: Option[Double] = None
  val objects = Buffer[String]()
  private var nodes: Option[Seq[(Shape, Pos)]] = None

  /**
   * Attempts to define a Seq[(Shape, Pos)] with the current 'objects' Buffer. Seq saved as option in  private var nodes on success.
   * @return true or false depending on whether it was successfully defined.
   */
  def defineObjectNodeParams(): Boolean =
    try
      val attributeList = objects.grouped(6)
      val shapePosList: Seq[(Shape, Pos)] = attributeList.map(n =>
        val length = n(1).toDouble
        val width = n(2).toDouble
        val rotation = n(3).toDouble
        val color = scalafx.scene.paint.Color.web(n(4))
        val position = n(5).split(',').map(_.trim.toDouble)
        val pos = Pos(position(0), position(1))
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
      case e: NumberFormatException =>
        false
      case g: IllegalArgumentException =>
        false
      case h: IndexOutOfBoundsException =>
        false
      case f: NoSuchElementException =>
        false
  def getNodeParams: Option[(String, Seq[(Shape, Pos)], Int, Double, Double)] =
    label.flatMap(n => layer.flatMap(l => rotation.flatMap(r => height.flatMap(h => nodes.map(s => (n, s, l, h, r))))))

  def isValid = label.isDefined && layer.isDefined && rotation.isDefined && height.isDefined && defineObjectNodeParams()

  override def toString = s"Object ${label.getOrElse("[Undefined]")} of height ${height.getOrElse("[Undefined]")}, rotation ${rotation.getOrElse("[Undefined]")}, at layer ${layer.getOrElse("[Undefined])}")} with objects ${nodes.getOrElse("[Undefined]")} has missing parameters."

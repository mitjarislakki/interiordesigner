package ID.gui
import ID.projects.Pos
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, Shape, Ellipse, Polygon}

import scala.collection.mutable.Buffer

class ObjectNode(_name: String, val shapes: Buffer[(Shape, Pos)], private var _layer: Int = 0) extends javafx.scene.layout.StackPane:
  def this(shape: Shape, pos: Pos) =
    this("", Buffer((shape, pos)))
  private val nameLabel = Label(_name)
  
  val baseShape = shapes.headOption.map(_._1)
  private val basePos = shapes.headOption.map(_._2)
  baseShape.foreach(shape => getChildren.addAll(shape, nameLabel))
  basePos.foreach( p =>
    this.setTranslateX(p.x)
    this.setTranslateY(p.y))

  def lengthTo(width: Double) = shapes.foreach((s: Shape, p: Pos) =>
    s match
      case rect: Rectangle => rect.setWidth(width)
      case oval: Ellipse => oval.setRadiusX(width/2)
      case tri: Triangle =>
      case _ =>
  )

  def widthTo(height: Double) = shapes.foreach((s: Shape, p: Pos) =>
    s match
      case rect: Rectangle => rect.setHeight(height)
      case oval: Ellipse => oval.setRadiusY(height/2)
      case tri: Triangle =>
      case _ =>
  )

  private var _vHeight = 0.0
  def vHeight = _vHeight

  def setVHeight(input: Double) =
    _vHeight = input

  def layer = _layer

  def setLayer(n: Int) =
    if n >= 0 then _layer = n

  def name = nameLabel.getText

  def setName(input: String) = nameLabel.setText(input)

  def setColor(color: Color) = baseShape.foreach(_.setFill(color))


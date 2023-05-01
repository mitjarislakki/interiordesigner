package ID.gui
import ID.projects.Pos
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, Shape, Ellipse, Polygon}

import scala.collection.mutable.Buffer

class ObjectNode(_name: String, val shapes: Buffer[(Shape, Pos)], private var _layer: Int = 0) extends javafx.scene.layout.StackPane:
  private val nameLabel = Label(_name)
  
  val main = shapes.headOption.map(_._1)
  main.foreach(shape => getChildren.addAll(shape, nameLabel))

  def widthTo(width: Double) = shapes.foreach( (s: Shape , p: Pos) =>
    s match
      case rect: Rectangle => rect.setWidth(width)
      case oval: Ellipse => oval.setRadiusX(width)
      case tri: Triangle =>
      case _ =>
  )

  def heightTo(height: Double) = shapes.foreach( (s: Shape , p: Pos) =>
    s match
      case rect: Rectangle => rect.setHeight(height)
      case oval: Ellipse => oval.setRadiusY(height)
      case tri: Triangle =>
      case _ =>
  )

  def setLayer(n: Int) =
    if n >= 0 then _layer = n

  def name = nameLabel.getText

  def setName(input: String) = nameLabel.setText(input)
  
  def setColor(color: Color) = main.foreach(_.setFill(color))


package ID.gui
import ID.projects.Pos
import javafx.collections.ObservableList
import scalafx.scene.control.{ContextMenu, Label, MenuItem}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Ellipse, Polygon, Rectangle, Shape}
import scalafx.event.EventHandler
import scalafx.scene.input.ContextMenuEvent
import scalafx.Includes.*
import scalafx.collections.ObservableBuffer

import scala.collection.mutable.Buffer

class ObjectNode(_name: String, initialShapes: Iterable[(Shape, Pos)], private var _layer: Int = 0, _height: Double = 0) extends javafx.scene.layout.StackPane:
  def this(tuple: (String, Iterable[(Shape, Pos)], Int)) =
    this(tuple._1, tuple._2, tuple._3)
  def this(shape: Shape, pos: Pos) =
    this("", Buffer((shape, pos)))


  private val nameLabel = Label(_name)
  nameLabel.setViewOrder(-1)


  val baseShape = initialShapes.headOption.map(_._1)
  private val basePos = initialShapes.headOption.map(_._2)

  def initialize() =
    baseShape.foreach(shape => getChildren.addAll(shape, nameLabel))
    basePos.foreach( p =>
      this.setTranslateX(p.x)
      this.setTranslateY(p.y)
      this.onContextMenuRequestedProperty.setValue(rightClick);
      initialShapes.tail.foreach((s: Shape, tp: Pos) =>
        getChildren.add(s);
        s.layoutX = tp.x;
        s.layoutY = tp.y
      )
    )

  def flipHorizontal() = getChildren.foreach(n => n match
    case v: javafx.scene.shape.Shape =>
      println("reached");
      v.scaleX = -v.getScaleX
    case _ =>)

  def lengthTo(width: Double) = initialShapes.foreach((s: Shape, p: Pos) =>
    s match
      case rect: Rectangle =>
        val initWidth = rect.getWidth;
        rect.setWidth(width)
      case oval: Ellipse => oval.setRadiusX(width/2)
      case tri: Triangle =>
      case _ =>
  )

  def widthTo(height: Double) = initialShapes.foreach((s: Shape, p: Pos) =>
    s match
      case rect: Rectangle => rect.setHeight(height)
      case oval: Ellipse => oval.setRadiusY(height/2)
      case tri: Triangle =>
      case _ =>
  )

  private var _vHeight = _height

  def vHeight = _vHeight
  def setVHeight(input: Double) =
    _vHeight = input

  def layer = _layer

  def setLayer(n: Int) =
    if n >= 0 then _layer = n

  def name = nameLabel.getText

  def setName(input: String) = nameLabel.setText(input)

  def setColor(color: Color) = baseShape.foreach(_.setFill(color))

  private def window = this.getScene.getWindow

  private val conMenu = new ContextMenu()
  val del = MenuItem("Delete")
  val flipH = MenuItem("Flip Horizontal")
  flipH.onAction = _ => flipHorizontal()
  conMenu.getItems.addAll(flipH, del)
  private def rightClick(e:ContextMenuEvent) = conMenu.show(window, e.screenX, e.screenY)

  def fetchAttributes =

    def rTDS(i: Double) = EventHelper.roundToDecimalString(i)

    def composeAttributeVector(n: javafx.scene.Node, isHead: Boolean = false) =
      val (x, y) = if isHead then (rTDS(this.getTranslateX), rTDS(this.getTranslateY)) else (rTDS(n.getLayoutX), rTDS(n.getLayoutY))
      n match
        case rectangle: javafx.scene.shape.Rectangle =>
          Vector(
            "- shape: rectangle",
            s"length: ${rTDS(rectangle.getWidth)}",
            s"width: ${rTDS(rectangle.getHeight)}",
            s"rotation: ${rTDS(rectangle.getRotate)}",
            s"color: ${rectangle.getFill.toString}",
            s"pos: $x, $y"
          )
        case ellipse: javafx.scene.shape.Ellipse =>
          Vector(
            "- shape: ellipse",
            s"length: ${rTDS(ellipse.getRadiusX)}",
            s"width: ${rTDS(ellipse.getRadiusY)}",
            s"rotation: ${rTDS(ellipse.getRotate)}",
            s"color: ${ellipse.getFill.toString}",
            s"pos: $x, $y"
          )
        case _ => Vector()


    val name = "- label: " + nameLabel.getText
    val rotation = "rotation: " + this.getRotate
    val height = "height: " + rTDS(vHeight)

    val nodeInformation = Vector(name, "layer: " + layer, rotation, height,  "shapes:")

    val shapes: ObservableBuffer[javafx.scene.Node] = getChildrenUnmodifiable.filter(n => n.isInstanceOf[javafx.scene.shape.Shape])
    val shapeInformation: Vector[Vector[String]] = shapes.headOption.map(f => composeAttributeVector(f, true)).getOrElse(Vector()) +: shapes.tail.map(f => composeAttributeVector(f, false)).toVector
    (nodeInformation, shapeInformation)

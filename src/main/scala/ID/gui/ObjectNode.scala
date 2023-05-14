package ID.gui
import ID.projects.Pos
import javafx.collections.ObservableList
import scalafx.scene.control.{ContextMenu, Label, MenuItem}
import scalafx.scene.layout.{Region, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Ellipse, Polygon, Rectangle, Shape}
import scalafx.event.EventHandler
import scalafx.scene.input.ContextMenuEvent
import scalafx.Includes.*
import scalafx.beans.property.IntegerProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.text.Text

import scala.collection.mutable.Buffer

class ObjectNode(_name: String, initialShapes: Iterable[(Shape, Pos)], _layer: Int = 1, _height: Double = 0, _rotation: Double = 0) extends javafx.scene.layout.StackPane:
  def this(tuple: (String, Iterable[(Shape, Pos)], Int, Double, Double)) =
    this(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5)
  def this(shape: Shape, pos: Pos) =
    this("", Buffer((shape, pos)))

  // track render order in parent
  private val layer = IntegerProperty(_layer)
  layer.onChange{ (source, oldVal, newVal) =>
    this.setViewOrder(- newVal.intValue())
  }

  // name of ObjectNode
  private val nameLabel = Text(_name)
  nameLabel.setViewOrder(-1)


  private val baseShape = initialShapes.headOption.map(_._1)
  private val basePos = initialShapes.headOption.map(_._2)

  def getBaseColor: javafx.scene.paint.Color =
    baseShape.map(_.getFill.asInstanceOf[javafx.scene.paint.Color]).getOrElse(Color.White)

  /**
   * Sets up the node
   */
  def initialize() =
    this.setViewOrder(-_layer)
    baseShape.foreach(shape => getChildren.addAll(shape, nameLabel))
    basePos.foreach( p =>
      this.setTranslateX(p.x)
      this.setTranslateY(p.y)
      this.setRotate(_rotation)
      this.onContextMenuRequestedProperty.setValue(rightClick);
      initialShapes.tail.foreach((s: Shape, tp: Pos) =>
        getChildren.add(s);
        s.layoutX = tp.x;
        s.layoutY = tp.y
      )
    )


  def lengthTo(width: Double) =
    baseShape.foreach(base =>
      val baseWidth = base match
        case rect: Rectangle => rect.getWidth
        case ellipse: Ellipse => ellipse.getRadiusX * 2
        case _ => throw Error("Unknown base shape")
      ;
      this.getChildren.foreach(node =>
          node match
            case rect: javafx.scene.shape.Rectangle =>
              val scale = rect.getWidth / baseWidth;
              rect.setWidth(scale * width)
            case ellipse: javafx.scene.shape.Ellipse =>
              val scale = ellipse.getRadiusX / baseWidth;
              ellipse.setRadiusX(scale * width)
            case _ =>
      )
    )

  def widthTo(height: Double) =
    baseShape.foreach(base =>
      val baseHeight = base match
        case rect: Rectangle => rect.getHeight
        case ellipse: Ellipse => ellipse.getRadiusY * 2
        case _ => throw Error("Unknown base shape")
      ;
      this.getChildren.foreach(node =>
          node match
            case rect: javafx.scene.shape.Rectangle =>
              val scale = rect.getHeight / baseHeight;
              rect.setHeight(scale * height)
            case ellipse: javafx.scene.shape.Ellipse =>
              val scale = ellipse.getRadiusY / baseHeight;
              ellipse.setRadiusY(scale * height)
            case _ =>
      )
    )

  private var _vHeight = _height

  def vHeight = _vHeight
  def setVHeight(input: Double) =
    _vHeight = input

  def getLayer = layer.value

  def setLayer(n: Int) =
    if n >= 0 then layer.value = n

  def name = nameLabel.getText

  def setName(input: String) = nameLabel.setText(input)

  def setColor(color: Color) = baseShape.foreach(_.setFill(color))

  private def window = this.getScene.getWindow

  private val conMenu = new ContextMenu()
  val del = MenuItem("Delete")
  conMenu.getItems.addAll(del)
  private def rightClick(e:ContextMenuEvent) = conMenu.show(window, e.screenX, e.screenY)

  /**
   * Fetches the attributes of this ObjectNode in YAML format for file saving
   * @return attributes
   */
  def fetchAttributes: (Vector[String], Vector[Vector[String]]) =

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
    end composeAttributeVector

    val name = "- label: " + nameLabel.getText
    val rotation = "rotation: " + this.getRotate.toString
    val height = "height: " + rTDS(vHeight)

    val nodeInformation = Vector(name, "layer: " + getLayer, rotation, height,  "shapes:")

    val shapes: ObservableBuffer[javafx.scene.Node] = getChildrenUnmodifiable.filter(n => n.isInstanceOf[javafx.scene.shape.Shape])
    val shapeInformation: Vector[Vector[String]] = shapes.headOption.map(f => composeAttributeVector(f, true)).getOrElse(Vector()) +: shapes.tail.map(f => composeAttributeVector(f, false)).toVector
    (nodeInformation, shapeInformation)
  end fetchAttributes



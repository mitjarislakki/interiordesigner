package ID.gui
import ID.projects.Pos
import scalafx.scene.control.{ContextMenu, Label, MenuItem}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Ellipse, Polygon, Rectangle, Shape}
import scalafx.event.EventHandler
import scalafx.scene.input.ContextMenuEvent
import scalafx.Includes.*

import scala.collection.mutable.Buffer

class ObjectNode(_name: String, initialShapes: Iterable[(Shape, Pos)], private var _layer: Int = 0) extends javafx.scene.layout.StackPane:
  def this(tuple: (String, Iterable[(Shape, Pos)], Int)) =
    this(tuple._1, tuple._2, tuple._3)
  def this(shape: Shape, pos: Pos) =
    this("", Buffer((shape, pos)))


  private val nameLabel = Label(_name)


  val baseShape = initialShapes.headOption.map(_._1)
  private val basePos = initialShapes.headOption.map(_._2)

  def initialize() =
    baseShape.foreach(shape => getChildren.addAll(shape, nameLabel))
    basePos.foreach( p =>
      this.setTranslateX(p.x)
      this.setTranslateY(p.y)
      this.onContextMenuRequestedProperty.setValue(rightClick)
    )
  def flipHorizontal() = getChildren.foreach(n => n match
    case v: javafx.scene.shape.Shape =>
      println("reached");
      v.scaleX = -v.getScaleX
    case _ =>)
  def lengthTo(width: Double) = initialShapes.foreach((s: Shape, p: Pos) =>
    s match
      case rect: Rectangle => rect.setWidth(width)
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

  private def window = this.getScene.getWindow
  private val conMenu = new ContextMenu()
  val del = MenuItem("Delete")
  val flipH = MenuItem("Flip Horizontal")
  flipH.onAction = _ => flipHorizontal()
  conMenu.getItems.addAll(flipH, del)
  private def rightClick(e:ContextMenuEvent) = conMenu.show(window, e.screenX, e.screenY)


package ID.projects
import scalafx.scene.paint.Color

trait Shape(private var _width: Double, private var _length: Double, private var _height: Double, private var _color: Color, val posOffset: (Int, Int, Int) = (0, 0, 0)):
  def color = _color
  def width = _width
  def length = _length
  def height = _height

  def area: Double
  def setColor(color: Color): Unit = _color = color
  def setPos(pos: Pos): Unit
  def rotate(amount: Double): Unit
  def scaleX(amount: Double): Unit =
    _width = width * amount
  def scaleY(amount: Double): Unit =
    _length = length * amount
  def scale(amount: Double): Unit =
    scaleX(amount)
    scaleY(amount)
/*
class Cuboid(width: Int, length: Int, height: Int, color: Color, posOffset: (Double, Double, Double)) extends Shape(length, width, height, color, posOffset):
  def area = width * length
  def setColor(color: Color) = ???
  def setPos(amount: Double) = ???
  def rotate(amount: Double) = ???
  def scaleX(amount: Double) = ???
  def scaleY(amount: Double) = ???
  def scale(amount: Double) = ???


class Cylinder(width: Int, length: Int, height: Int, color: Color, posOffset: (Double, Double, Double)) extends Shape(length, width, height, color, posOffset):
  def area = Math.PI * width * length
  def setColor(color: Color) = ???
  def setPos(amount: Double) = ???
  def rotate(amount: Double) = ???
  def scaleX(amount: Double) = ???
  def scaleY(amount: Double) = ???
  def scale(amount: Double) = ???
*/
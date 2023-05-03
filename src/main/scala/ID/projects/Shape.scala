package ID.projects
import scalafx.scene.paint.Color

trait Shape(private var _width: Double, private var _length: Double, private var _height: Double, private var _color: Color, private var _pos: Pos, val posOffset: (Double, Double, Double) = (0, 0, 0)):
  def color = _color
  def width = _width
  def length = _length
  def height = _height
  def pos = _pos

  def area: Double

  def setColor(color: Color): Unit =
    _color = color
  def setPos(pos: Pos): Unit =
    _pos = pos

  def rotate(amount: Double): Unit = println("lol")

  def scaleX(amount: Double): Unit =
    _width = width * amount

  def scaleY(amount: Double): Unit =
    _length = length * amount

  def scale(amount: Double): Unit =
    scaleX(amount)
    scaleY(amount)

class Rectangle(width: Int, length: Int, height: Int, color: Color, pos: Pos, posOffset: (Double, Double, Double)) extends Shape(length, width, height, color, pos, posOffset):
  def area = width * length

class Oval(width: Int, length: Int, height: Int, color: Color, pos: Pos, posOffset: (Double, Double, Double)) extends Shape(length, width, height, color, pos, posOffset):
  def area = Math.PI * width * length

class Triangle(width: Int, length: Int, height: Int, color: Color, pos: Pos, posOffset: (Double, Double, Double)) extends Shape(length, width, height, color, pos, posOffset):
  def area = width * length / 2.0

package ID.projects
import java.awt.Color

trait Shape(width: Double, length: Double, height: Double, color: Color, posOffset: (Double, Double, Double) = (0, 0, 0)):
  def area: Double
  def setColor(color: Color): Unit
  def setPos(pos: Pos): Unit
  def rotate(amount: Double): Unit
  def scaleX(amount: Double): Unit
  def scaleY(amount: Double): Unit
  def scale(amount: Double): Unit
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
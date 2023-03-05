package ID.projects
import java.awt.Color

trait Shape(length: Double, width: Double, height: Double, color: Color, posOffset: (Double, Double, Double)):
  def area: Double
  def setColor(color: Color): Unit
  def setPos(pos: Pos): Unit
  def rotate(amount: Double): Unit
  def scaleX(amount: Double): Unit
  def scaleY(amount: Double): Unit
  def scale(amount: Double): Unit
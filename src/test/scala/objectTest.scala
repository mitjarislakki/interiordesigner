
abstract class floorplanObject:
  def lol: Unit

trait shape extends floorplanObject:
  def area: Double
  def deez: Unit
end shape

object test extends shape:
  def lol = println("lol")
  def area = 2
  def deez = println("deez")

enum EditingMode:
  case lol
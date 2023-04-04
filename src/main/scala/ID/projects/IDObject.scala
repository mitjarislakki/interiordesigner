package ID.projects
import scala.collection.mutable.Buffer

class IDObject(private var _layer: Int, private var _name: String, val shapes: Buffer[Shape], private var currentPos: Pos):
  val mainShape = shapes.headOption

  def layer = _layer
  def name = _name
  def pos = currentPos

  def area = ???
  
  def setPos(targetPos: Pos): Unit =
    mainShape.foreach(
      shape =>
        shape.setPos(targetPos);
        shapes.tail.foreach(
        childShape => childShape.setPos(targetPos.offsetBy(childShape.posOffset))
      )
    )
  
  def setLayer(n: Int) =
    _layer = n

  def setName(newName: String) =
    _name = newName
  
  def overlapsWith(another: IDObject) = ???
  
  def distanceFrom(pos: Pos) = ???
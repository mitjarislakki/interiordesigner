package ID.projects
import scala.collection.mutable.Buffer

class IDObject(private var layer: Int, private var name: String, private val shapes: Buffer[Shape], private var currentPos: Pos):
  def area = ???
  def setPos(pos: Pos) =
    currentPos = pos
  def setLayer(n: Int) =
    layer = n
  def overlapsWith(another: IDObject) = ???
  def distanceFrom(pos: Pos) = ???
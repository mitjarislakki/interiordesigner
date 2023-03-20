package ID.projects
import scala.collection.mutable.Buffer

class Project(val objects: Buffer[IDObject]):

  def objectsAtLayer(n: Int) = objects.filter(_.layer==n)
  def addObject(ob: IDObject): Unit = objects += ob
  def removeObject(ob: IDObject): Unit = ???
  def transformObject(ob: IDObject, f: IDObject => IDObject): Unit = ???

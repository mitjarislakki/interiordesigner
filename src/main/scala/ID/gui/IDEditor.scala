package ID.gui

import ID.projects.{IDObject, Pos, Project}
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.*

import scala.collection.mutable.Buffer

class IDEditor(project: Project) extends Pane:
  def initialize = ???

val rec = new Rectangle:
  x = 0
  y = 0
  width = 50
  height = 50
  fill = Blue
val testNode = ObjectNode("test", scala.collection.mutable.Buffer((rec, new Pos(0, 0, 0))))
object tempEditor extends IDEditor(new Project("lol", Buffer[IDObject]())):
  tempEditor.children = testNode
  EventHelper.makeDraggable(testNode)

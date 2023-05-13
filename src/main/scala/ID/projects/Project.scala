package ID.projects
import ID.gui.ObjectNode

import scala.collection.mutable.Buffer
import ID.files.ONContender
import scalafx.scene.shape.Shape
import ID.gui.ObjectNode

class Project(val name: String, val furniture: Seq[(String, Seq[(Shape, Pos)], Int)] = Seq()):
  def initializeNodes = furniture.map(ObjectNode(_))

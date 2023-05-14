package ID.gui

import ID.projects.{Pos, Project}
import ID.files.ONContender
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.*
import scalafx.Includes.jfxNode2sfx
import scalafx.collections.ObservableBuffer
import scalafx.scene.shape.Rectangle

import scala.collection.mutable.Buffer

class IDEditor(project: Project) extends Pane:
  var projectName: String = project.name
  var ceilingHeight = project.initHeight
  prefWidth = project.initLength
  prefHeight = project.initWidth
  style = "-fx-background-color: white"
  
  def objects = this.children

  def objectNodes: ObservableBuffer[ObjectNode] = this.children.filter(_.isInstanceOf[ObjectNode]).map(_.asInstanceOf[ObjectNode])

  def objectsAtLayer(n: Int) =
    val found = scala.collection.mutable.ListBuffer[ObjectNode]()
    objects.foreach(node => node match
      case v: ObjectNode => if v.getLayer == n then found += v
      case _ =>)
    found

  val selectedNode = new ObjectProperty[ObjectNode]

  def initialize(): Unit =
    this.children.addAll(project.initializeNodes)



package ID.gui

import ID.projects.{IDObject, Pos, Project}
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
  style = "-fx-background-color: white"
  objects.foreach(node => node match
  case v: ObjectNode => EventHelper.makeDraggable(v, true, objectsAtLayer)
  case _ => )
  
  def objects = this.children

  def objectsAtLayer(n: Int) =
    val found = scala.collection.mutable.ListBuffer[ObjectNode]()
    objects.foreach(node => node match
      case v: ObjectNode => if v.layer == n then found += v
      case _ =>)
    found

  val selectedNode = new ObjectProperty[ObjectNode]

  def initialize =
    this.children.addAll(project.furniture.map(n =>
      val tuple = n.composeObjectNode;
      ObjectNode(tuple._1, tuple._2, tuple._3)
    ))


val rec = new Rectangle:
  x = 0
  y = 0
  width = 50
  height = 50
  fill = Blue

val rec2 = new Rectangle:
  x = 0
  y = 0
  width = 80
  height = 32
  fill = Green

val testNode = ObjectNode("test", scala.collection.mutable.Buffer((rec, new Pos(0, 0, 0))))
val testNode2 = ObjectNode("test2", scala.collection.mutable.Buffer((rec2, new Pos(0, 0, 0))))

object tempEditor extends IDEditor(new Project("lol", Buffer[ONContender]())):
  this.setPrefWidth(500)
  this.setPrefHeight(500)



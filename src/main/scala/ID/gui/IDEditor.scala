package ID.gui

import ID.projects.{IDObject, Pos, Project}
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.*
import scalafx.Includes.jfxNode2sfx

import scala.collection.mutable.Buffer

class IDEditor(project: Project) extends Pane:
  def objects = this.children
  val selectedNode = new ObjectProperty[Node]
  def initialize = ???

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

object tempEditor extends IDEditor(new Project("lol", Buffer[IDObject]())):
  this.setPrefWidth(500)
  this.setPrefHeight(500)
  objects ++= List(testNode, testNode2)
  objects.foreach(node => EventHelper.makeDraggable(node))

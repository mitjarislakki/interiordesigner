package ID.gui

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ListCell, ListView}
import scalafx.scene.layout.{GridPane, Region}
import javafx.scene.Node
import scalafx.geometry.Pos


object ONList extends GridPane():
  minWidth = Region.USE_PREF_SIZE
  def setInput(input: ObservableBuffer[Node]) = println("This is a temporary setInput method for ONList.scala // TODO")
  val contents = List("Bananas", "Apples", "Pairs", "Lychees", "Strawberries", "Blueberries")
  val nodeList = new ListView(contents)
  add(nodeList, 0, 0)
  def setContent(nodes: Iterable[ObjectNode]) = ???
  class ObjectNodeCell extends ListCell[String]:
    alignment = Pos.Center

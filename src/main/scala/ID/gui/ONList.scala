package ID.gui

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ListCell, ListView}
import scalafx.scene.layout.GridPane
import javafx.scene.Node
import scalafx.geometry.Pos


class ONList(input: ObservableBuffer[Node]) extends GridPane():
  val contents = List("Bananas", "Apples", "Pairs", "Lychees", "Strawberries", "Blueberries")
  val nodeList = new ListView(contents)
  add(nodeList, 0, 0)
  def setContent(nodes: Iterable[ObjectNode]) = ???
  class ObjectNodeCell extends ListCell[String]:
    alignment = Pos.Center

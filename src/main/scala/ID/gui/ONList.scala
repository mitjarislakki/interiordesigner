package ID.gui

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ContentDisplay, ListCell, ListView}
import scalafx.scene.layout.{GridPane, Priority, Region}
import javafx.scene.Node
import scalafx.geometry.Pos
import scalafx.scene.input.{ClipboardContent, DragEvent, MouseDragEvent, MouseEvent, TransferMode}
import scalafx.Includes.*

import java.util


object ONList extends GridPane():
  minWidth = Region.USE_PREF_SIZE
  def setInput(input: ObservableBuffer[Node]) =
    ""
  val contents: ObservableBuffer[String] = ObservableBuffer()
  private val contBuf = ObservableBuffer[String]()
  val nodeList = new ListView(contents):
    cellFactory = ((param: ListView[String]) => new oNCell())
  add(nodeList, 0, 0)
  nodeList.vgrow = Priority.Always
  def setContent(nodes: Iterable[ObjectNode]) = ???


  private class oNCell() extends scalafx.scene.control.ListCell[String]:
    val thisCell: ListCell[_] = this
    contentDisplay = ContentDisplay.TextOnly
    alignment = Pos.Center


    this.onDragDetected = (event: MouseEvent) =>
      if !(this.getItem == null) then
        val items = this.getListView.getItems
        val dragboard = startDragAndDrop(TransferMode.Move)
        val content = new ClipboardContent
        content.putString(this.getItem)
        dragboard.setContent(content)
        event.consume()


    this.onDragOver = (event: DragEvent) =>
      if (event.gestureSource.ne(thisCell) && event.getDragboard.hasString) then
        event.acceptTransferModes(TransferMode.Move)
      event.consume()


    this.onDragEntered = (event: DragEvent) =>
      if (event.getGestureSource.ne(thisCell) && event.getDragboard.hasString) then
        this.setOpacity(0.4)


    this.onDragExited = (event: DragEvent) =>
      if (event.getGestureSource.ne(thisCell) && event.getDragboard.hasString) then
        this.setOpacity(1)


    this.onDragDropped = (event: DragEvent) =>
      if !(this.getItem == null) then
        val db = event.getDragboard
        var success = false
        if db.hasString then
          val items = this.getListView.getItems
          val draggedIdx = items.indexOf(db.getString)
          val thisIdx = items.indexOf(this.getItem)
          items.set(draggedIdx, this.getItem)
          items.set(thisIdx, db.getString)
          val itemsCopy = new util.ArrayList[String](items)
          this.getListView.getItems.setAll(itemsCopy)
          success = true
        event.setDropCompleted(success)
        event.consume()


    this.onDragDone = e  => e.consume()


    def updateItem(item: String, empty: Boolean): Unit =
        this.text = item


    this.item.onChange( (_, _, newVal) => text = newVal)

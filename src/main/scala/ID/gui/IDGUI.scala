package ID.gui

import javafx.geometry.VerticalDirection
import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.paint.Color.*
import scalafx.scene.{Node, Scene}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent, ScrollEvent}
import scalafx.scene.control.{Button, ButtonType, Dialog, Label, Menu, MenuBar, MenuItem, SeparatorMenuItem, TextField, ToolBar}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, ColumnConstraints, CornerRadii, GridPane, Pane, Priority, RowConstraints}
import scalafx.scene.text.Font
import ID.projects.Project
import ID.files.IDReader
import scalafx.Includes.*
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.event
import scalafx.event.ActionEvent
import scalafx.scene.shape.Rectangle
import scalafx.stage.FileChooser.ExtensionFilter

import scala.util.Random

object IDGUI extends scalafx.application.JFXApp3.PrimaryStage:

  width = 800
  height = 500


  val primaryMonitor = java.awt.Toolkit.getDefaultToolkit.getScreenSize
  val root = GridPane()
  root.gridLinesVisible = true
  this.scene = new Scene(parent = root)

  title = "Interior Designer"


  val outerPane = ZoomableScrollPane
  setEditor(tempEditor)
  val oNView = ONList
  root.add(IDMenu.menuBar, 0, 0, 3, 1)
  root.add(IDToolbar, 0, 1, 1, 2)
  root.add(outerPane, 1, 1, 1, 2)
  root.add(IDOProperties, 2, 1, 1, 1)
  root.add(oNView, 2, 2)
  root.autosize()


  // helper functions to create fast column & row constraints
  outerPane.hgrow = Priority.Always
  outerPane.vgrow = Priority.Always
  oNView.vgrow = Priority.Always
  IDToolbar.vgrow = Priority.Always


  // new project event

  IDMenu.newProject.onAction = (event) =>
    if offerSave() then
      val result = Dialogs.newProjectDialog()
      result.foreach(project =>
        val newEditor = IDEditor(project);
        setEditor(newEditor)
      )


  private def offerSave(): Boolean =
    val current = outerPane.currentChild
    val saveCondition = current.flatMap(currentEditor => Dialogs.wishToSave())
    saveCondition match
      case Some(true) => saveToFile()
      case _ =>
    current.isEmpty || saveCondition.isDefined

  // file chooser for "open project" -button in menu
  def openFile() =
    if offerSave() then
      val t = new scalafx.stage.FileChooser()
      t.setTitle("Choose a project file with .YAML format")
      val file = t.showOpenDialog(this)
      if file != null then
         if (file.getName.endsWith(".YAML")) then
           Some(IDReader.readProject(file)).foreach(pro =>
             println("Println in IDGUI.scala (line 62)" + pro.furniture);
             setEditor(IDEditor(pro));
             updateTitle()
           )
         else
           updateTitle()
  IDMenu.openProject.onAction = e => openFile()

  // save project
  def saveToFile() =
    val t = new scalafx.stage.FileChooser()
    t.title = "Save Project"
    t.getExtensionFilters.addAll(new ExtensionFilter("Project File", "*.YAML*"))
    t.initialFileName= "*.YAML"
    val file = t.showSaveDialog(this)
    if file != null then
      outerPane.currentChild.foreach(editor => IDReader.writeProjectToFile(file, editor))
  IDMenu.saveProject.onAction = e => saveToFile()


  private var notification: Option[String] = None
  def updateTitle() =
    val name = outerPane.currentChild match
      case Some(n) => n.projectName
      case None => "[No Project selected]"
    this.title = "Interior Designer - " + name + " - " + notification.getOrElse("[No notifications]")

  def setEditor(editor: IDEditor) =
    outerPane.setChild(editor)
    ONList.setInput(editor.children)
    setListeners(editor)
    editor.initialize()


  def setListeners(editor: IDEditor) =

    // Fetches current selectedNode in Option
    def currentSelection: Option[ObjectNode] = Option(editor.selectedNode.value)

    // event handlers for ObjectNodes in editor
    def makeSelectable(node: javafx.scene.Node) =
      node match
        case a: ObjectNode =>
          // select node on click if SEL tool in use
          a.onMousePressed = (event: MouseEvent) => if IDToolbar.select.isSelected then editor.selectedNode.value = a
          // refresh IDOProperties on node drag if SEL tool in use
          a.addEventHandler(MouseEvent.MouseDragged, (event: MouseEvent) => if IDToolbar.select.isSelected then currentSelection.foreach(n => IDOProperties.update(n)))
        case _ =>

    // change listener for new nodes in children of editor
    editor.objects.onChange { (obs, chs) =>
      for change <- chs do
        change match
          case ObservableBuffer.Add(_, list) =>
            list.foreach(node => node match
              case v: ObjectNode =>
                v.initialize();
                makeSelectable(v);
                EventHelper.makeDraggable(v, IDToolbar.select.isSelected, editor.objectsAtLayer);
                v.del.onAction = e => editor.children.remove(v)
              case _ =>
            )
          case ObservableBuffer.Remove(_, list) => list.foreach(node => node match
            case v: ObjectNode =>
            case _ =>
          )
          case _ =>
    }

    // Rectangle drag tool
    EventHelper.rectOnDrag(editor, IDToolbar.addRectangle.isSelected)

    // Ellipse drag tool
    EventHelper.circOnDrag(editor, IDToolbar.addCircle.isSelected)

    // TODO switch to Object Node editor
    IDToolbar.oNEdit.onAction = (event: ActionEvent) => println("nice")

    // Update IDProperties text field values on node change
    editor.selectedNode.onChange((_, _, newNode) =>
      IDOProperties.update(newNode);
    )


    /** listeners for IDOProperties input**/
    // name
    IDOProperties.nameBox.onKeyTyped = (event: KeyEvent) =>
      currentSelection.foreach(node => node.setName(IDOProperties.nameBox.getText))
    // X pos
    IDOProperties.xBox.onKeyTyped = (event: KeyEvent) =>
        currentSelection.foreach(node => IDOProperties.xBox.getText.toDoubleOption.foreach(node.setTranslateX(_)))
    // Y pos
    IDOProperties.yBox.onKeyTyped = (event: KeyEvent) =>
        currentSelection.foreach(node => IDOProperties.yBox.getText.toDoubleOption.foreach(node.setTranslateY(_)))
    // length
    IDOProperties.lBox.onKeyTyped = (event: KeyEvent) =>
      currentSelection.foreach(node =>
        IDOProperties.lBox.getText.toDoubleOption match
          case Some(n) if n>0 =>
            node.lengthTo(n)
          case _ =>
        )
    // width
    IDOProperties.wBox.onKeyTyped = (event: KeyEvent) =>
      currentSelection.foreach(node =>
        IDOProperties.wBox.getText.toDoubleOption match
          case Some(n) if n>0 =>
            node.widthTo(n)
          case _ =>
        )
    // height
    IDOProperties.hBox.onKeyTyped = (event: KeyEvent) =>
      currentSelection.foreach(node =>
        IDOProperties.hBox.getText.toDoubleOption match
          case Some(n) if n>0 => node.setVHeight(n)
          case _ =>
      )
    //rotation
    IDOProperties.rBox.onKeyTyped = (event: KeyEvent) =>
      currentSelection.foreach(node =>
        IDOProperties.rBox.getText.toDoubleOption.foreach(n => node.rotate = n)
      )
    // layer
    IDOProperties.layerBox.onKeyTyped = (event: KeyEvent) =>
      currentSelection.foreach(node =>
        IDOProperties.layerBox.getText.toIntOption match
          case Some(n) if n>=0 => node.setLayer(n)
          case _ =>
        )
    // color
    IDOProperties.colorPicker.onAction = (event: ActionEvent) =>
      currentSelection.foreach(node => node.setColor(IDOProperties.colorPicker.getValue))
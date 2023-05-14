package ID.gui

import ID.projects.Project
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{Alert, ButtonType, Dialog, Label, TextField}
import scalafx.scene.layout.{GridPane, Region}
import scalafx.Includes.*
import scalafx.beans.property.StringProperty
import scalafx.scene.control.Alert.AlertType

object Dialogs:

  /**
   * Helper class to easily compose Dialogs with result type [T]
   * @param ti the Dialog title text
   * @param btn the Dialog confirmation button text
   * @tparam T the Dialog result type
   */
  class projectDialog[T](ti: String, btn: String) extends Dialog[T]():
      title = ti

      val okButtonType = new ButtonType(btn, ButtonData.OKDone)
      this.dialogPane().buttonTypes = Seq(okButtonType, ButtonType.Cancel)

      private val grid = new GridPane():
        hgap = 10
        vgap = 10
        padding = Insets(20, 100, 10, 10)

      private def lab(in: String) = new Label(in)

      private val okButton = this.getDialogPane.lookupButton(okButtonType)
      okButton.disable = true


      private val name = new TextField():
        promptText = "Project name"
      private val clength = new TextField():
        promptText = "Must be at least 200"
      private val cwidth = new TextField():
        promptText = "Must be at least 200"
      private val cheight = new TextField():
        promptText = "Set as '-1' to ignore"

      private def rTD(n: String) = EventHelper.roundToDecimalString(n.toDouble).toDouble
      def tuple: Option[(String, Double, Double, Double)] =
        if validParams then Some( (name.getText, rTD(clength.getText), rTD(cwidth.getText), rTD(cheight.getText)) )
        else None

      private def validParams: Boolean = clength.getText.toDoubleOption.exists(_>200) && cwidth.getText.toDoubleOption.exists(_>200) && cheight.getText.toDoubleOption.exists(_ >= -1)
      private def validOnChange(in: StringProperty*) = in.foreach(_.onChange{ (_, _, _) =>
        if validParams then okButton.disable = false
        else okButton.disable = true
      })
      validOnChange(clength.text, cwidth.text, cheight.text)

      val contents = List(lab("Project name:"), name, lab("Floorplan length:"), clength, lab("Floorplan width:"), cwidth, lab("Ceiling height:"), cheight)

      var contentIndex = 0
      for i <- 0 until contents.size/2 do
            for j <- 0 to 1 do
                  val node = contents(contentIndex)
                  node.minWidth = Region.USE_PREF_SIZE
                  node.minHeight = Region.USE_PREF_SIZE
                  grid.add(contents(contentIndex), j, i)
                  contentIndex += 1
      this.dialogPane().content = grid
  end projectDialog

  /**
   *
   */
  def editProjectProperties() =
    val alert = projectDialog[Option[(String, Double, Double, Double)]]("Edit project", "Set")
    alert.resultConverter = dialogButton =>
      if (dialogButton == alert.okButtonType) then
        alert.tuple
      else None
    alert.showAndWait()
    alert.getResult

  /**
   * Pop-up dialog that prompts whether the user wants to save the current dialog to file.
   * @return Some(true) on "Save", Some(false) on "Discard" and None on Cancel.
   */
  def wishToSave() =
    val alert = new Dialog[Option[Boolean]]():
      title = "Save project?"
      headerText = "This action will replace the current project"
      contentText = "Save the current project?"
      val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
      val discardButtonType = new ButtonType("Discard", ButtonData.OKDone)
      this.dialogPane().buttonTypes = Seq(saveButtonType, discardButtonType, ButtonType.Cancel)
      this.resultConverter = dialogButton =>
        if dialogButton == saveButtonType then Some(true)
        else if dialogButton == discardButtonType then Some(false)
        else
          None
      this.showAndWait()
    alert.getResult


  /**
   * Pop-up dialog that allows creating a new Project with a given name & dimensions.
   * @return Option[Project]
   */
  def newProjectDialog() =
    val alert = projectDialog[Option[Project]]("Create new project", "Create")
    alert.resultConverter = dialogButton =>
      if (dialogButton == alert.okButtonType) then
        alert.tuple.map((name: String, length: Double, width: Double, height: Double) => Project(name, (length, width, height)))
      else None
    alert.showAndWait()
    alert.getResult
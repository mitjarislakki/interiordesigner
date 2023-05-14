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
    val alert = new Dialog[Option[Project]]():
  
      title = "Create new project"
  
      val okButtonType = new ButtonType("Create", ButtonData.OKDone)
      this.dialogPane().buttonTypes = Seq(okButtonType, ButtonType.Cancel)
  
      val grid = new GridPane():
        hgap = 10
        vgap = 10
        padding = Insets(20, 100, 10, 10)
  
      def lab(in: String) = new Label(in)
  
      val okButton = this.getDialogPane.lookupButton(okButtonType)
      okButton.disable = true
  
  
      val name = new TextField():
        promptText = "Project name"
      val clength = new TextField():
        promptText = "Must be at least 200"
      val cwidth = new TextField():
        promptText = "Must be at least 200"
      val cheight = new TextField():
        promptText = "Set as '-1' to ignore"
  
      def validParams: Boolean = clength.getText.toDoubleOption.exists(_>200) && cwidth.getText.toDoubleOption.exists(_>200) && cheight.getText.toDoubleOption.exists(_ >= -1)
      def validOnChange(in: StringProperty*) = in.foreach(_.onChange{ (_, _, _) =>
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
  
      this.resultConverter = dialogButton =>
        if (dialogButton == okButtonType) then
          def rTD(n: String) = EventHelper.roundToDecimalString(n.toDouble).toDouble
          Some(Project(name.getText, (rTD(clength.getText), rTD(cwidth.getText), rTD(cheight.getText))))
        else None
      this.dialogPane().content = grid
      this.showAndWait()
    alert.getResult
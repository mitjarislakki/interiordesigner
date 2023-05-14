package ID.files

import java.io.*
import scala.collection.mutable.Buffer
import ID.projects.*
import ID.gui.{IDEditor, ObjectNode}
import scalafx.scene.shape.Shape

object IDFileHandler:

  /**
   * Takes a file and returns a Project-object.
   * @param file File to read from
   * @return Returns Option[Project] and information string as tuple
   */
  def readProject(file: File): (Option[Project], String) =
    try
      val fileIn =FileReader(file)

      val lineReader = BufferedReader(fileIn)

      var projectName: Option[String] = None // gets value of project name if in file
      var dimensions: Option[(Double, Double, Double)] = None

      var currentSection: Option[String] = None // major section
      var inSubSection = false // subsection check for shapes
      val objectbuffer = Buffer[ONContender]() // accumulator for Object Node contenders

      def conditionedDoubleLift(col: Array[String], i: Int, con: Double => Boolean): Option[Double] =
        col.lift(i).flatMap(s => s.trim.toDoubleOption match {case Some(d) if con(d) => Some(d); case _ => None})

      var lineCount = 0
      try
        var inputLine = lineReader.readLine()
        while inputLine != null do
          val trimmed = inputLine.trim
          lineCount += 1
          if trimmed.startsWith("name:") && projectName.isEmpty then projectName = Some(inputLine.trim.drop(5).trim) // add project name
          if trimmed.startsWith("dimensions:") && dimensions.isEmpty then dimensions = // add project dimensions
            val potential = inputLine.trim.drop(11).split(',')
            val first = conditionedDoubleLift(potential, 0, 0.<)
            val second = conditionedDoubleLift(potential, 1, 0.<)
            val third = conditionedDoubleLift(potential, 2, -1.<=)
            first.flatMap(f => second.flatMap(s => third.map(t => (f, s,t))))

          if inputLine == "idobjects:" then currentSection = Some(inputLine) // set section to IDObjects

          currentSection match
            case Some("idobjects:") =>
              val key = inputLine.trim.split(':').head
              val value = inputLine.trim.split(':').tail.mkString.trim
              if key == "- label" then // initialize new furniture object
                inSubSection = false
                val addition = new ONContender
                addition.startingLine = lineCount
                objectbuffer += addition
                addition.label = Some(value)
              else if key == "layer" then // set layer to furniture object
                objectbuffer.lastOption.foreach(_.layer = Some(value.toInt))
              else if key == "rotation" && !inSubSection then
                objectbuffer.lastOption.foreach(_.rotation = value.toDoubleOption)
              else if key == "height" then
                objectbuffer.lastOption.foreach(_.height = value.toDoubleOption)
              else if inSubSection then // add to layer
                objectbuffer.lastOption.foreach(_.objects += value)
              else if key == "shapes" then inSubSection = true
            case _ =>
          inputLine = lineReader.readLine()
        end while
      catch
        case e: IOException =>
      finally
        lineReader.close()
      if projectName.isEmpty then (None, "Project name undefined")
      else if dimensions.isEmpty then (None, "Project dimensions undefined")
      else
        objectbuffer.find(!_.isValid) match
          case Some(value) => (None, s"Import failed, something missing in object starting on ${value.startingLine}")
          case None => (Some(Project(projectName.get, dimensions.get, (objectbuffer.toSeq.map(_.getNodeParams.get)))), "Project loaded")
    catch
      case e: FileNotFoundException => (None, "File not found")
      case g: IOError => (None, "IO Error encountered")
  end readProject

  /**
   * Takes a File and IDEditor as parameters, saving the editor's content as a project in .YAML format
   * @param file File to write to
   * @param editor IDEditor to extract Project from
   */
  def writeProjectToFile(file: File, editor: IDEditor): String =
    try
      val fileOut =
        FileWriter(file)
      val name = editor.projectName
      val dimensions = s"${editor.getWidth}, ${editor.getHeight}, ${editor.ceilingHeight}"
      val objects = editor.objectNodes

      val fileWriter = BufferedWriter(fileOut)


      def writeLine(in: String, indent: Int = 0) =
        val effectiveIndent = " " * (if in.startsWith("- ") then indent-2 else indent)
        fileWriter.newLine()
        fileWriter.write(effectiveIndent + in)

      fileWriter.write(s"name: $name")
      writeLine((s"dimensions: $dimensions"))
      writeLine(("idobjects:"))
      for oN <- objects do
        val (nodeInfo, shapeInfo) = oN.fetchAttributes
        nodeInfo.foreach(n => writeLine(n, 4))
        shapeInfo.foreach(f => f.foreach(n => writeLine(n, 8)))
      fileWriter.close()
      "Project saved"
    catch
      case e: FileNotFoundException => "File not found"
      case g: IOError => "Error writing to file"
  end writeProjectToFile

  /**
   * Importing saved objects
   *
   * @param file File to read from
   * @return Returns objects and information string
   */
  def fetchSavedObjects(file: File): (Option[Seq[(String, Seq[(Shape, Pos)], Int, Double, Double)]], String) =
    val fileIn =
      try
        FileReader(file)
      catch
        case e: FileNotFoundException => throw Error("File not found")

    val lineReader = BufferedReader(fileIn)

    var currentSection: Option[String] = None // major section
    var inSubSection = false // subsection check for shapes
    val objectbuffer = Buffer[ONContender]() // accumulator for Object Node contenders

    var lineCount = 0
    try
      var inputLine = lineReader.readLine()
      while inputLine != null do
        val trimmed = inputLine.trim
        lineCount += 1

        val key = inputLine.trim.split(':').head
        val value = inputLine.trim.split(':').tail.mkString.trim
        if key == "- label" then // initialize new furniture object
          inSubSection = false
          val addition = new ONContender
          addition.startingLine = lineCount
          objectbuffer += addition
          addition.label = Some(value)
        else if key == "layer" then // set layer to furniture object
          objectbuffer.lastOption.foreach(_.layer = Some(value.toInt))
        else if key == "rotation" && !inSubSection then
          objectbuffer.lastOption.foreach(_.rotation = value.toDoubleOption)
        else if key == "height" then
          objectbuffer.lastOption.foreach(_.height = value.toDoubleOption)
        else if inSubSection then // add to layer
          objectbuffer.lastOption.foreach(_.objects += value)
        else if key == "shapes" then inSubSection = true
        inputLine = lineReader.readLine()
      end while
    catch
      case e: IOException =>
    finally
      lineReader.close()
    objectbuffer.find(!_.isValid) match
      case Some(value) => (None, s"Import failed, something missing in object starting on ${value.startingLine}")
      case None => (Some(objectbuffer.toSeq.map(_.getNodeParams.get)), "Import success")
end IDFileHandler

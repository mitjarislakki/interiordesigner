package ID.files

import java.io.*
import scala.collection.mutable.Buffer
import ID.projects.*
import ID.gui.ObjectNode

object IDReader:
  def readProject(file: File): Project =
    val fileIn =
      try
        FileReader(file)
      catch
        case e:FileNotFoundException=> throw Error("File not found")

    val lineReader = BufferedReader(fileIn)

    var projectName: Option[String] = None // gets value of project name if in file
    var currentSection: Option[String] = None // major section
    var inSubSection = false // subsection check for shapes
    val objectbuffer = Buffer[ONContender]() // accumulator for Object Node contenders

    var lineCount = 0
    try
      var inputLine = lineReader.readLine()
      while inputLine != null do
        lineCount += 1
        if inputLine.startsWith("name:") && projectName.isEmpty then projectName = Some(inputLine.drop(5).trim) // add project name

        if inputLine == "idobjects:" then currentSection = Some(inputLine) // set selection to IDObjects

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
            else if inSubSection then // add to layer
              objectbuffer.lastOption.foreach(_.objects += value)
            else if key == "shapes" then inSubSection = true
          case _ =>
        inputLine = lineReader.readLine()
      end while
    catch
      case e: IOException => throw Error("Some weird IO error lol")
    finally
      lineReader.close()
    objectbuffer.foreach(i => if !i.defineObjectNodeParams() then throw Error(s"Error with nodes on line ${i.startingLine}"))
    val name = projectName.getOrElse(throw LabelUndefinedError("Project name undefined"))
    val furniture = objectbuffer.map(cont => cont.getNodeParams.getOrElse(throw Error(cont.toString))).toSeq
    Project(name, furniture)

  end readProject

  def myLs() =
    val currentDirectory = new File("./")
    val listing = currentDirectory.listFiles()

    for oneFile <- listing do
      if oneFile.isFile then
        println(oneFile.length + " bytes\t " + oneFile.getName)
  end myLs

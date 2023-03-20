package ID.files

import java.io.*
import scala.collection.mutable.Buffer

object Reader:
  def readFile(): Unit =
    val fileIn =
      try
        FileReader("ExampleProject.YAML")
      catch
        case e:FileNotFoundException=> println("file not found"); return

    val lineReader = BufferedReader(fileIn)

    var projectName: Option[String] = None
    var currentSection: Option[String] = None
    var floorPlan: Option[String] = None
    val objectbuffer = Buffer[Array[String]]()

    var objectStepper = 0
    try
      var inputLine = lineReader.readLine()
      while inputLine != null do
        inputLine = lineReader.readLine()

        if inputLine.startsWith("Name:") && projectName.isEmpty then projectName = Some(inputLine.drop(5)) // add project name

        if inputLine == "IDObjects:" then currentSection = Some(inputLine) // set selection to IDObjects

        currentSection match
          case Some("IDObjects:") =>
            if inputLine.startsWith("-") then

              objectbuffer += new Array[String](8)
              objectStepper = 0
            objectbuffer.lastOption.foreach(n => n(0) = inputLine.substring(inputLine.indexOf(':')).trim)

          case _ =>

    catch
      case e: IOException => throw Error("Some weird IO error lol")
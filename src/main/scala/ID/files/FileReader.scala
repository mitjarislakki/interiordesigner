package ID.files

import java.io.*

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
    try
      var inputLine = lineReader.readLine()
      while inputLine != null do
        inputLine = lineReader.readLine().trim
        if inputLine.startsWith("Name:") && projectName.isEmpty then projectName = Some(inputLine.drop(5)) // add project name
        if inputLine == "IDObjects:" then currentSection = Some(inputLine)

    catch
      case e: IOException => throw Error("Some weird IO error lol")
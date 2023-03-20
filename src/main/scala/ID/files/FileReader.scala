package ID.files

import java.io.*
import scala.collection.mutable.Buffer
import ID.projects._

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
              objectbuffer += new Array[String](9)
              objectStepper = 0
            objectbuffer.lastOption.foreach(n => n(objectStepper) = inputLine.substring(inputLine.indexOf(':')).trim)
            objectStepper += 1
          case _ =>
      end while
    catch
      case e: IOException => throw Error("Some weird IO error lol")
    finally
      lineReader.close()
    if projectName.isEmpty then throw Error("Project name undefined")
    else if objectbuffer.exists(_.contains(null)) then throw Error("Some object has missing parameters")
    else
      Project(projectName.get,
        objectbuffer.map(a =>
          val name = a(0)
          val layer = a(1).toInt
          val length = a(3)
          val width = a(4)
          val height = a(5)
          val rotation = a(6)
          val color = a(7)
          val pos =
            val asArray = a(8).split(',').map(_.trim.toInt)
            Pos(asArray(0), asArray(1), asArray(2))
          val shape = a(2)
          IDObject(layer, name, Buffer[Shape](), pos)
        )
      )

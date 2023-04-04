package ID.files

import java.io.*
import scala.collection.mutable.Buffer
import ID.projects._

class IDReader(file: File):
  def readFile(): Project =
    val fileIn =
      try
        FileReader(file)
      catch
        case e:FileNotFoundException=> throw Error("File not found")

    val lineReader = BufferedReader(fileIn)

    var projectName: Option[String] = None
    var currentSection: Option[String] = None
    var floorPlan: Option[String] = None
    val objectbuffer = Buffer[Array[String]]()

    var objectStepper = 0
    try
      var inputLine = lineReader.readLine()
      while inputLine != null do
        if inputLine.startsWith("name:") && projectName.isEmpty then projectName = Some(inputLine.drop(5).trim) // add project name

        if inputLine == "idobjects:" then currentSection = Some(inputLine) // set selection to IDObjects

        currentSection match
          case Some("idobjects:") =>
            if inputLine.trim.startsWith("-") then
              objectbuffer += new Array[String](9)
              objectStepper = 0
            objectbuffer.lastOption.foreach(n => n(objectStepper) = inputLine.split(':')(1).trim)
            objectStepper += 1
          case _ =>
        inputLine = lineReader.readLine()
      end while
    catch
      case e: IOException => throw Error("Some weird IO error lol")
    finally
      lineReader.close()
    if projectName.isEmpty then throw Error("Project name undefined")
    if objectbuffer.exists(_.contains(null)) then throw Error("Some object has missing parameters")
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
  end readFile

  def myLs() =
    val currentDirectory = new File("./")
    val listing = currentDirectory.listFiles()

    for oneFile <- listing do
      if oneFile.isFile then
        println(oneFile.length + " bytes\t " + oneFile.getName)
  end myLs

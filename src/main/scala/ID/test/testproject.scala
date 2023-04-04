package ID.test

import ID.files.IDReader

@main def Testing =
 val file = new java.io.File("src/main/scala/ID/test/ExampleProject.YAML")
 val reader = IDReader(file)
 reader.myLs()
 val testproject = reader.readFile()
 println(s"The project name is ${testproject.name} and the objects are ${testproject.objects}")
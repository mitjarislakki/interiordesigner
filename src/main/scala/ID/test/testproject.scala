package ID.test

import ID.files.IDReader

@main def Testing() =
 val file = new java.io.File("src/main/scala/ID/test/ExampleProject.YAML")
 val reader = IDReader
 reader.myLs()
 val testproject = reader.readProject(file)
 println(s"The project name is ${testproject.name} and the objects are ${testproject.furniture}")
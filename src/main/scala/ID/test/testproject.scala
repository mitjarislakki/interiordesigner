package ID.test

import ID.files.MyReader

@main def Testing =
 val reader = MyReader("src/main/scala/ID/test/ExampleProject.YAML")
 reader.myLs()
 val testproject = reader.readFile()
 println(s"The project name is ${testproject.name} and the objects are ${testproject.objects}")
package ID.gui
import scalafx.scene.Scene
import scalafx.scene.control.{Label, Menu, MenuBar, MenuItem, SeparatorMenuItem}


object IDMenu:
   val menuBar = new MenuBar

   val fileMenu = new Menu("File")
     val newProject = new MenuItem("New project")
     val editProject = new MenuItem("Edit project")
     val openProject = new MenuItem("Open project")
     val saveProject = new MenuItem("Save project")
     val exitItem = new MenuItem("Exit")

   val editMenu = new Menu("Edit")

   val viewMenu = new Menu("View")

   val helpMenu = new Menu("Help")


   fileMenu.items = List(newProject, editProject, openProject, saveProject, new SeparatorMenuItem, exitItem)
   menuBar.menus = List(fileMenu, editMenu, viewMenu, helpMenu)


package ID.gui
import scalafx.scene.Scene
import scalafx.scene.control.{Label, Menu, MenuBar, MenuItem, SeparatorMenuItem}


object IDMenu:
   val menuBar = new MenuBar

   val fileMenu = new Menu("File")
     val newProject = new MenuItem("New project")
     val openProject = new MenuItem("Open project")
     val saveProject = new MenuItem("Save project")
     val exitItem = new MenuItem("Exit")

   val editMenu = new Menu("Edit")

   val viewMenu = new Menu("View")

   val helpMenu = new Menu("Help")


   fileMenu.items = List(newProject, openProject, saveProject, new SeparatorMenuItem, exitItem)
   menuBar.menus = List(fileMenu, editMenu, viewMenu, helpMenu)


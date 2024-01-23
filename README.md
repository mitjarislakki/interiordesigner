_This repository was created as part of the Programming Studio 2 -course at Aalto University in Spring 2023. This ReadMe serves as a project document (part of submission)._

# Interior Designer (14.5.2023)

## General description

Project topic: 330 – Interior Design

The program allows placing furniture of various sizes on a customizable floor plan of an apartment. The chosen difficulty was ‘demanding’ but the completed work is probably closer to medium difficulty with what I’d say are still the major elements of the demanding task.

Rather than reading a floor plan image, the user can only create a floor plan from scratch using shapes.

## User interface

You can launch the program by running the object Designer in package ID.gui

Users interact with the program through a graphical interface with no direct console commands available. Everything is done via mouse input or keyboard input fields. You can zoom in/out in the editor using Ctrl + Scroll when hovering over the editor
 The left houses the toolbar with options for mouse actions
-	SEL – select – click on an object to select it
-	MOVE – move – drag an object to select it & move it with your mouse
-	REC – rectangle – drag draw a rectangle on screen from its top left corner to bottom right corner
-	CIRC – ellipse – drag draw an ellipse on screen from its top left corner to bottom right corner
When you add a rectangle or circle object, they default to layer 1. Objects can’t overlap with other objects on the same layer or layer 0 (Objects on layer 0 can overlap with objects on other layers as when you’re building the floor plan it would be a nuisance to get stuck on objects from other layers)
You can delete an object by right clicking on it and then clicking “Delete”
 
From the File menu in the menu bar:
-	New project – Create a new project
-	Edit project – Edit current project name or dimensions
-	Open project – Open an existing project from a specified file
- Save project – Save the existing project to a specified file
-	Exit – close the program

**(There are some example project files in src/main/scala/ID/test)**

You’ll sometimes get notifications about your actions in the title of the window. For example, for a broken project file:

## Program structure

The program is split into three main sub-parts, the packages ID.files, ID.gui and ID.projects.

ID.files is for file handling. It contains the following:
-	Errors – Some classes extending Error to implement better error handling.
-	IDFileHandler - object to handle reading from & writing to files.
-	ONContender -  class to be composed into ObjectNodes for the GUI

ID.gui makes up most of the program with multiple different UI elements and helpers:
-	Designer - The main application (JFXApp3),  used to start/run the program.
-	Dialogs - object Dialogs contains methods for custom pop-up Dialogs that return the result of user actions in the Dialog.
-	EventHelper - object EventHelper with general & EventHandler help methods.
-	IDEditor - The user’s canvas where objectNodes (furniture/other objects) are added
-	IDGUI - The primary stage of the main application, where all the UI elements are added together and methods/handlers requiring access to multiple classes are implemented.
-	IDMenu.scala – The MenuBar with types MenuItem
-	IDOProperties- The editor’s selected node’s properties in a GridPane with types Label & TextField. Used to edit object properties via keyboard
-	IDToolbar – the left ToolBar with ToggleButton tool selection
-	ObjectNode – Class making up the furniture/shape objects in the editor. Most functions in the program are related to these.
-	ONList – List of all ObjectNodes in the current editor.
-	Triangle – Incomplete class for implementing Triangles
-	ZoomableScrollPane – a ScrollPane wrapper for the editor so the user can zoom in/out

ID.projects is used to initialize read files into the GUI:
-	Pos – Used for initial dimensions of floorplans or furniture/shape positions
-	Project – Simple project information received from file read

ID.test just contains example projects. 

Most objects contain private methods to add event listeners for changes in the state of the program, for example setListeners() in IDGUI.

The structure has changed significantly compared to the initial technical plan as a lot of transformations are much easier to implement inside the GUI itself rather than a separate backend-structure since the scalafx objects & classes already contain the bulk of the required transforms and running a separate structure just adds overhead & unnecessary complexity.

## Algorithms

The algorithms in use are rather simple and mostly related to transformations of shapes.

For example moving via mouse drag: simply moving to the mouse’s coordinates doesn’t account for the position of the cursor on the object, which isn’t a smooth experience. Instead:
-	Mouse drag started: save mouse cursor’s initial coordinates initialX, initialY
-	Mouse dragged:
  - Check current mouse coordinates newX, newY and difference of new & initial coordinates: deltaX & deltaY
  - Increment object coordinates by deltaX & deltaY
  - Check if object overlaps with other objects in the same layer or layer 0, set coordinates back to original if so

Most necessary transformations are available as methods in the graphics library, so the underlying algorithms aren’t visible.

## Data structures

Most data structures are related to ScalaFX/JavaFX methods. The library implements the mutable data types ObservableBuffer[T] & ObjectProperty[T], which act akin to call-by-name wrappers of ‘var’ that can be used to track changes via event handling. I use ObjectProperty[ObjectNode] to track the editor’s selected node and IntegerProperty to track ObjectNode layers.
Calling the method .getChildren for a Node returns an ObservableBuffer[Node] so its use is also prevalent in the program. ObservableBuffer is a mutable data structure with support for event handling.

For file handling, types are usually composed of some Iterable[T] with basic types String, Double, Int or similar as passing javafx Node types can be dangerous due to what seems like random orders of operation on compile, which can make the program crash on startup due to a Toolkit initialization error.
Files and internet access

The program supports file saving and opening projects from files. The files are in .YAML format followingly:
```
name: My appartment
dimensions: 1000.0, 1000.0, -1.0
idobjects::
- label: Desk
  layer: 2
  rotation: 0.0
  height: 70.0
  shapes:
    - shape: rectangle
      length: 200.0
      width: 60.0
      rotation: 0.0
      color: 0x994d00ff
      pos: 227.6, 67.9
```
Every project requires a name, dimensions and idobjects. An object can have as many shapes as it wants. The position of the first shape determines the position on-screen and the position of following shapes an offset from the first, for example a hotplate:
```
- label: Hotplate
  layer: 1
  rotation: 0
  height: 0
  shapes:
  - shape: rectangle
    length: 33
    width: 33
    rotation: 0
    color: D3D3D3
    pos: 60, 82, 40
  - shape: ellipse
    length: 14
    width: 14
    rotation: 0
    color: 0xa0522dff
    pos: 3, 3, 10
```
## Testing

Due to the graphical nature of the program most testing was done with the GUI & some debugging via temporary println -methods. Unit testing UI elements is hard due to the aforementioned Toolkit initialization and the implemented objects also utilize the graphics library, deviating from the technical plan.

The planned testing process was more thorough, but the following tests have been carried out through the user interface:
-	Set illegal values in object properties fields
-	Set illegal values in project settings fields
-	Open file with wrong file format
-	Open empty project file
-	Open fully fledged project file
-	Open project file with missing values
-	Open project with illegal values

## Known bugs and missing features

Bugs
-	Setting a custom color in the ObjectNode property color picker sometimes minimizes the window. This might be a listener or default behavior that has to be overwritten.
-	Rather than an ObjectNode rotating in a coordinate system, the whole coordinate system of a Node rotates causing the origin to no longer be in the top left corner of its bounds. It’s possible that this is achievable via keeping track of bounds coordinates rather than the coordinates of the ObjectNode itself.
-	Labels can extend past shapes making ObjectNode bounds larger than they need to be to fit its shapes. This also causes the node to move as it’s recentered due to the nature of the StackPane
-	ObjectNode overlap bounds are from the ObjectNode. There’s probably a way to do this that I haven’t found yet.
-	Bounds for rotated ellipses are larger than they should be for some reason. Not sure how I’d fix this.
-	The project name sometimes doesn’t update on file load. Might be a problem with a StringProperty on Change listener.

Missing features
-	Although supported by the ObjectNode class, it’s not possible to add more than one basic shape to an ObjectNode inside of the editor, only via file reading. This would require the ObjectNode editor which is a large task as the editor & adjacent properties are currently only suitable for editing ObjectNodes and not their composing Shapes directly.
-	It’s not possible to select multiple objects at once. It would be very useful for drag moves. A possible way to implement this is to add a rectangle drag selection that filters all nodes that intersect with it, then form a draggable Group() with said nodes as content
-	Ceiling height is implemented but not used anywhere. ObjectNodes have a height attribute & so does the project so would simply require a check for the total height of intersecting objectnodes on separate layers, then compared to the project’s ceiling height.
-	Edit, View and Help tabs in the menu bar don’t do anything. Edit could have mouse transform actions, view additional ObjectNode methods to hide/show things like bounds & labels. Help could bring up a user guide like the one written above..
-	The bottom right empty space is meant for ONList, a reorderable ListView of all ObjectNodes in order to easily select one. I’ll talk more about ONList later in this document.
-	The original plan had an idea for cable routing measurement. As mentioned in my technical plan, a simple way would be to measure distance in (x, y, z) from one point to another and sum the length, although that would require functionality to attach a wire to an ObjectNode in order to figure out the ceiling height coordinate.

## 3 best sides and weaknesses

### Strengths

I’m fairly happy with the implementation of ObjectNode shapes apart from intersecting bounds as it does appear highly scalable into more complex objects.
The file handling also seems to work well.

Although quite different from the technical plan, I also think the class structure is pretty well made. Classes are cohesive and only coupled via IDGUI for the most part, although IDGUI does have wide access to a lot of class methods since the classes extend those of ScalaFX, so perhaps some more complex structure could result in looser coupling at the cost of readability & time.

### Weaknesses

ObjectNode still has problems. The bounds sometimes work questionably, noticeably larger than the content, and I haven’t managed to get a Label object to wrap around existing bounds instead of extending them.

For project attributes in edit properties, the default text of the fields is left blank although an existing name and dimensions are surely already defined. This should be a somewhat simple fix but implementation in its current form would take a fair amount of time as the Dialogs don’t have access to the active IDEditor.
While ScalaFX is supposed to be a wrapper for JavaFX, I had to import and utilize classes & objects from JavaFX at times for pattern matching as implicit conversions from scalafx.Includes.* did not work. This might be a limitation of the library or oversight on my part.

## Deviations from the plan, realized process and schedule

Based on the progress log:

21.2.-6.3.2023: I created the classes and methods defined in my UML diagram from the technical plan without implementing most of them. 3-4 hours

7.3-20.3.2023: GUI library changed from Swing to ScalaFX. Set up the preliminary layout and stage/window components. 8-9 hours

21.3.-3.4.2023: Implemented file reading with the format & classes in-use at the time. 3-4 hours

4.4.-17.4.2023: Implemented mouse drag & keyboard property editing for objects with many issues with shape coordinates. First large deviations from technical plan with class ObjectNode & backend which caused me to ask for a time extension. 15+ hours

18.4.-8.5.2023: I caught covid with a high fever off-and-on for around two weeks which made it much harder. Added many more methods to ObjectNode & added object creation buttons in the UI. ~20 hours

9.5-14.5.2023: I’ve implemented file saving, object overlapping, menu bar methods, window resize constraints, ObjectNode layer property rendering order, zoom in/out in ZoomableScrollPane. 25+ hours

The planned schedule did not match reality at all as I seriously underestimated how difficult working with the GUI is.

The overwhelmingly largest issue with the plan was the graphics library. I realized in April that the planned class layout simply does not work for the program as mutable objects would just add overhead since the GUI itself already has collections and methods to accomplish most of what I wanted.

The objects being manipulated in the program are displayed as Nodes in ScalaFX with various methods to transform them. As such, the planned backend only really matters as a midway point to convert a project from a file into a ScalaFX form, e.g. to Nodes in a Pane in the case of IDEditor.

## Final evaluation

I feel like my ideas and constructs for the program are sound but fall short in the lack of full implementation. ONList is incomplete and very challenging to implement as my previous attempts caused runtime errors and there’s a lack of clear documentation, especially for ScalaFX. I also found no simple ways to create easily transformable triangles in scalafx.scene.shape.

The main thing missing is an ObjectNode editor to create complex objects, and a way to import saved ObjectNodes as buttons to easily place copies into an editor pane such as windows, doors, outlets and so on which would really improve what can be achieved with the editor.

A smaller quality of life feature would be the editor pane centering on the scrollpane, but despite multiple different tries I didn’t manage to get it to work either.

With the current class structure I don’t think the data structures can be improved much apart from maybe implementing more ObjectProperty[T] - values to replace variables. I can’t think of a vastly better class structure for this library either.

If I started again from the beginning, I would definitely try and put more time into it at the start just to familiarize myself with the GUI library, perhaps also investigate more library options as I’m not really sure how different it would’ve been to implement in Swing like my original plan. I’m also fairly confident in my current class structure so I wouldn’t have wasted time on my original as well.

## References

ScalaFX documentation: https://javadoc.io/doc/org.scalafx/scalafx_3.0.0-RC3/latest/index.html

Oracle JavaFX tutorials: https://docs.oracle.com/javase/8/javase-clienttechnologies.htm

Stack Overflow: https://stackoverflow.com/

Though not implemented in my program in a working capacity, I’d like to explicitly mention this drag & drop reorder ListView from ‘jewelsea’ for javafx, that I utilized for similar functionality in ONList.scala: https://stackoverflow.com/questions/20412445/how-to-create-a-reorder-able-tableview-in-javafx

## Appendixes

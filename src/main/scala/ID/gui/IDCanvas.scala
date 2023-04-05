package ID.gui

import ID.projects.Project
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.*

object IDCanvas extends Canvas(100, 100):
 val g = this.graphicsContext2D
 g.fill = White
 g.fillRect(0, 0, this.getWidth, this.getHeight)
 val v = new Rectangle:
  x = 100
  y = 100
  width = 50
  height = 50
  fill = Blue
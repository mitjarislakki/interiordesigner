package ID.gui

import ID.projects.Project
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color.*

object IDCanvas extends Canvas(1920, 1080):
 val g = this.graphicsContext2D
 g.fill = White
 g.fillRect(0, 0, this.getWidth, this.getHeight)
package ID.gui
import scalafx.scene.shape.Polygon

class Triangle(width: Double, height: Double) extends Polygon:
  points ++= Seq(
    0, height,
    width, height,
    width/2, 0)



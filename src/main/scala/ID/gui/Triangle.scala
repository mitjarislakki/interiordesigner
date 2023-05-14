package ID.gui
import scalafx.scene.shape.Polygon

/**
 * THIS IS A WORK IN PROGRESS SHAPE NOT CURRENTLY IMPLEMENTED IN THE PROGRAM
 * @param width width of the triangle
 * @param height height of the triangle
 */
class Triangle(width: Double, height: Double) extends Polygon:
  points ++= Seq(
    0, height,
    width, height,
    width/2, 0)



import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Point2D}
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text

import scalafx.Includes.*
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Point2D
import scalafx.scene.Scene
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.stage.{WindowEvent, StageStyle}

object graphicTest extends JFXApp3:
  val v = new Rectangle:
      x = 100
      y = 100
      width = 50
      height = 50
      fill = Blue

  override def start() =
    var shapeLocation: Option[Point2D] = None
    stage = new scalafx.application.JFXApp3.PrimaryStage:
      width = 500
      height = 500
      val root = new BorderPane()
      scene = new Scene(parent = root)
      val initPos = new Point2D(v.getX, v.getY)
      var anchorPoint: Option[Point2D] = None
      v.onMousePressed = (event: MouseEvent) =>
        anchorPoint = Some(new Point2D(event.screenX, event.screenY))

      v.onMouseDragged = (event: MouseEvent) =>
        anchorPoint.foreach(point =>
          val sL = shapeLocation.getOrElse(initPos);
          v.setX(sL.x + event.screenX - point.x);
          v.setY(sL.y + event.screenY - point.y))
      v.onMouseReleased = (event: MouseEvent) =>
        shapeLocation = Some(new Point2D(v.getX, v.getY))
        println(shapeLocation.toString)


      root.children += v

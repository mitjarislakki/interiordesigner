import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Point2D}
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.*
import scalafx.scene.shape.{Circle, Cylinder, Ellipse, Rectangle}
import scalafx.Includes.*
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Point2D
import scalafx.scene.Scene
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color
import scalafx.stage.{StageStyle, WindowEvent}

object graphicTest extends JFXApp3:
  val v = new Rectangle:
      x = 100
      y = 100
      width = 50
      height = 50
      fill = Blue
  val s = new Rectangle:
    x = 200
    y = 250
    width = 20
    height = 40
    fill = Green

  override def start() =
    stage = new scalafx.application.JFXApp3.PrimaryStage:
      width = 500
      height = 500
      val root = new BorderPane()
      scene = new Scene(parent = root)
      root.children.addAll(v, s)
      var shapeLocation: Option[Point2D] = None
      root.children.foreach( node =>
        val initPos = new Point2D(node.getTranslateX, node.getTranslateY)
        var anchorPoint: Option[Point2D] = None
        ;
        node.onMousePressed = (event: MouseEvent) =>
          anchorPoint = Some(new Point2D(event.screenX, event.screenY))
        ;
        node.onMouseDragged = (event: MouseEvent) =>
          anchorPoint.foreach(point =>
            val sL = shapeLocation.getOrElse(initPos);
            node.translateX = (sL.x + event.screenX - point.x);
            node.translateY = (sL.y + event.screenY - point.y))
        ;
        node.onMouseReleased = (event: MouseEvent) =>
          shapeLocation = Some(new Point2D(node.getTranslateX, node.getTranslateY))
          println(shapeLocation.toString)
      )



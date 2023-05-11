package ID.gui
import scalafx.scene.control.ScrollPane
import scalafx.scene.input.ScrollEvent
import scalafx.Includes.*
import scalafx.event.EventIncludes.eventClosureWrapperWithParam
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Group, Node}
import scalafx.scene.layout.{CornerRadii, StackPane, VBox}

class ZoomableScrollPane(child: Node) extends ScrollPane:
  val bgfill = scalafx.scene.layout.BackgroundFill(scalafx.scene.paint.Color.LightSkyBlue, CornerRadii.Empty, Insets.Empty)
  val bg = new scalafx.scene.layout.Background(Array(bgfill))
  this.setBackground(bg)
  val outer = Group(child)
  content = outer
  pannable = false
  fitToHeight = true
  fitToWidth = true

  outer.addEventHandler(ScrollEvent.Scroll, (event) =>
    if event.isControlDown then
      event.consume()
      val zoomFactor = 1.1
      val deltaY = event.getDeltaY
      val deltaX = event.getDeltaX
      if deltaY < 0 then
        child.scaleX.value /= zoomFactor ;
        child.scaleY.value /= zoomFactor
      else if deltaY > 0 then
        child.scaleX.value *= zoomFactor ;
        child.scaleY.value *= zoomFactor ;
      ;
      else
        child.translateX = child.getTranslateX + deltaX;
        child.translateY = child.getTranslateY + deltaY

  )
end ZoomableScrollPane

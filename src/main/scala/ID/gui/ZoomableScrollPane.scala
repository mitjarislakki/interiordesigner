package ID.gui
import scalafx.scene.control.ScrollPane
import scalafx.scene.input.ScrollEvent
import scalafx.Includes.*
import scalafx.event.EventIncludes.eventClosureWrapperWithParam
import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.layout.CornerRadii

class ZoomableScrollPane(child: Node) extends ScrollPane:
  val bgfill = scalafx.scene.layout.BackgroundFill(scalafx.scene.paint.Color.LightSkyBlue, CornerRadii.Empty, Insets.Empty)
  val bg = new scalafx.scene.layout.Background(Array(bgfill))
  this.setBackground(bg)
  content = child
  pannable = false
  fitToHeight = false
  fitToWidth = false

  this.addEventHandler(ScrollEvent.Scroll, (event) =>
    val zoomFactor = 1.1
    val deltaY = event.getDeltaY
    val deltaX = event.getDeltaX
    if event.isControlDown then
      if deltaY < 0 then
        child.scaleX.value /= zoomFactor ;
        child.scaleY.value /= zoomFactor
      else if deltaY > 0 then
        child.scaleX.value *= zoomFactor ;
        child.scaleY.value *= zoomFactor ;
      event.consume();
    else
      child.translateX = child.getTranslateX + deltaX;
      child.translateY = child.getTranslateY + deltaY

  )
end ZoomableScrollPane

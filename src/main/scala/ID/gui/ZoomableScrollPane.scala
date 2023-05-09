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
  pannable = true
  fitToHeight = false
  fitToWidth = false
  this.onScroll = (event) =>
    val zoomFactor = 1.1 // adjust this to change zoom sensitivity
    val deltaY = event.deltaY.get
    if (deltaY < 0)
      child.scaleX.value /= zoomFactor
      child.scaleY.value /= zoomFactor
    else if (deltaY > 0)
      child.scaleX.value *= zoomFactor
      child.scaleY.value *= zoomFactor
    event.consume()
end ZoomableScrollPane

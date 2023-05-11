package ID.gui

import scalafx.collections.ObservableBuffer
import scalafx.scene.Node
import scalafx.scene.control.ListView
import scalafx.scene.layout.GridPane


class ONList(tracked: ObservableBuffer[javafx.scene.Node]) extends GridPane():
  val map = tracked.groupBy(node => node match
    case v: ObjectNode => v.layer
    case _ => -1)
  tracked.onChange { (obs, chs) =>
    for change <- chs do
      change match
        case ObservableBuffer.Add(_, list) =>
          list.foreach(node => node match
            case v: ObjectNode =>
            case _ =>
          )
        case ObservableBuffer.Remove(_, list) => list.foreach(node => node match
          case v: ObjectNode =>
          case _ =>
        )
        case _ =>
  }

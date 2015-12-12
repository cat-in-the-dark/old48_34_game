package com.catinthedark.lib.constants

import java.lang
import javafx.beans.value.{ObservableValue, ChangeListener}
import javafx.scene.control._
import javafx.scene.layout.HBox


/**
  * Created by over on 11.12.15.
  */
class OnOffControl(val onOff: OnOff, val el: HBox) {
  val checkbox = el.lookup("#checkbox").asInstanceOf[CheckBox]
  val button = el.lookup("#button").asInstanceOf[Button]

  checkbox.setText(onOff.name)
  checkbox.selectedProperty.addListener(new ChangeListener[java.lang.Boolean] {
    override def changed(observableValue: ObservableValue[_ <: lang.Boolean], t: lang.Boolean, t1: lang.Boolean) = {
      onOff.now = t1
    }
  })
}

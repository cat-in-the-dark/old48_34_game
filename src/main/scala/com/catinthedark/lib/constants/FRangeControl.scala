package com.catinthedark.lib.constants

import javafx.beans.value.{ObservableValue, ChangeListener}
import javafx.event.EventHandler
import javafx.scene.control.{Button, Slider, TextField, Label}
import javafx.scene.input.{MouseEvent, KeyEvent, KeyCode}
import javafx.scene.layout.VBox

/**
  * Created by over on 11.12.15.
  */
class FRangeControl(val fRange: FRange, val el: VBox) {
  val label = el.lookup("#label").asInstanceOf[Label]
  val tx = el.lookup("#input").asInstanceOf[TextField]
  val slider = el.lookup("#slider").asInstanceOf[Slider]
  val button = el.lookup("#button").asInstanceOf[Button]

  label.setText(fRange.name)

  tx.setText(fRange.now.toString)
  tx.textProperty.addListener(new ChangeListener[String] {
    override def changed(observableValue: ObservableValue[_ <: String], t: String, t1: String) = {
      try {
        t1.toFloat
      } catch {
        case e: NumberFormatException =>
          tx.textProperty.setValue(t)
      }
    }
  })

  slider.setMin(fRange.from)
  slider.setMax(fRange.to)
  slider.setValue(fRange.now)
  slider.setBlockIncrement((fRange.to - fRange.from).toDouble / 100)

  slider.valueProperty().addListener(new ChangeListener[Number] {
    override def changed(observableValue: ObservableValue[_ <: Number], t: Number, t1: Number) = {
      tx.textProperty.setValue(t1.intValue.toString)
      fRange.set(slider.getValue.toFloat)
    }
  })

  tx.setOnKeyPressed(new EventHandler[KeyEvent] {
    override def handle(t: KeyEvent) = {
      if (t.getCode() == KeyCode.ENTER)
        slider.setValue(tx.getText.toDouble)
    }
  })

  button.setOnMouseClicked(new EventHandler[MouseEvent] {
    override def handle(t: MouseEvent) = {
      println("dump me to file!")
    }
  })
}

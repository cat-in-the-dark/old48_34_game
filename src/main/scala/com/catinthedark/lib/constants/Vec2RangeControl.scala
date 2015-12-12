package com.catinthedark.lib.constants

import javafx.beans.value.{ObservableValue, ChangeListener}
import javafx.event.EventHandler
import javafx.scene.control.{Button, Slider, TextField, Label}
import javafx.scene.input.{MouseEvent, KeyCode, KeyEvent}
import javafx.scene.layout.VBox

import com.badlogic.gdx.math.Vector2

/**
  * Created by over on 12.12.15.
  */
class Vec2RangeControl(val vec2Range: Vec2Range, val el: VBox) {
  val label = el.lookup("#label").asInstanceOf[Label]
  label.setText(vec2Range.name)

  val tx = el.lookup("#input").asInstanceOf[TextField]
  val slider = el.lookup("#slider").asInstanceOf[Slider]


  tx.setText(vec2Range.now.x.toString)
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

  slider.setMin(vec2Range.from.x)
  slider.setMax(vec2Range.to.x)
  slider.setValue(vec2Range.now.x)
  slider.setBlockIncrement((vec2Range.to.x - vec2Range.from.x).toDouble / 100)

  slider.valueProperty().addListener(new ChangeListener[Number] {
    override def changed(observableValue: ObservableValue[_ <: Number], t: Number, t1: Number) = {
      tx.textProperty.setValue(t1.intValue.toString)
      vec2Range.set(value = new Vector2(slider.getValue.toFloat, vec2Range.now.y))
    }
  })

  tx.setOnKeyPressed(new EventHandler[KeyEvent] {
    override def handle(t: KeyEvent) = {
      if (t.getCode() == KeyCode.ENTER)
        slider.setValue(tx.getText.toDouble)
    }
  })

//////////?????????????///

  val tx2 = el.lookup("#input1").asInstanceOf[TextField]
  val slider2 = el.lookup("#slider1").asInstanceOf[Slider]

  tx2.setText(vec2Range.now.y.toString)
  tx2.textProperty.addListener(new ChangeListener[String] {
    override def changed(observableValue: ObservableValue[_ <: String], t: String, t1: String) = {
      try {
        t1.toFloat
      } catch {
        case e: NumberFormatException =>
          tx2.textProperty.setValue(t)
      }
    }
  })

  slider2.setMin(vec2Range.from.y)
  slider2.setMax(vec2Range.to.y)
  slider2.setValue(vec2Range.now.y)
  slider2.setBlockIncrement((vec2Range.to.y - vec2Range.from.y).toDouble / 100)

  slider2.valueProperty().addListener(new ChangeListener[Number] {
    override def changed(observableValue: ObservableValue[_ <: Number], t: Number, t1: Number) = {
      tx2.textProperty.setValue(t1.intValue.toString)
      vec2Range.set(value = new Vector2(vec2Range.now.x, slider2.getValue.toFloat))
    }
  })

  tx2.setOnKeyPressed(new EventHandler[KeyEvent] {
    override def handle(t: KeyEvent) = {
      if (t.getCode() == KeyCode.ENTER)
        slider2.setValue(tx2.getText.toDouble)
    }
  })

}

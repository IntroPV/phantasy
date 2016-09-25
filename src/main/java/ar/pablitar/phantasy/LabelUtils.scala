package ar.pablitar.phantasy

import java.awt.Color
import java.awt.Font
import com.uqbar.vainilla.appearances.Label

object LabelUtils {
  val defaultTextColor = Color.WHITE
  val defaultTextSize = 120
  def damageText(text: String, color: Color = defaultTextColor, textSize: Int = defaultTextSize) =
    new Label(new Font(Font.SANS_SERIF, Font.PLAIN, defaultTextSize), color, text).center()
}
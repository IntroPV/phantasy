package ar.pablitar.phantasy

import java.awt.Color
import java.awt.Font
import com.uqbar.vainilla.appearances.Label

object LabelUtils {
  val defaultTextColor = Color.WHITE
  val lifeFontSize = 40
  val nameFontSize = (lifeFontSize * 0.75).toInt
  val defaultTextSize = 120
  
  def font(size: Int) = new Font(Font.SANS_SERIF, Font.PLAIN, size) 
  
  def damageText(text: String, color: Color = defaultTextColor, textSize: Int = defaultTextSize) =
    new Label(font(defaultTextSize), color, text).center()
    
  val lifeFont = font(lifeFontSize)
  def lifeLabel(initialText: String) = new Label(lifeFont, Color.ORANGE, initialText).center()
  
  val nameFont = font(nameFontSize)
  def warriorNameLabel(name: String) = new Label(nameFont, Color.LIGHT_GRAY, name).center() 
}
package ar.pablitar.phantasy

import ar.pablitar.vainilla.commons.components.RichGameComponent
import ar.pablitar.vainilla.commons.math.Orientation
import com.uqbar.vainilla.appearances.Label
import java.awt.Graphics2D
import com.uqbar.vainilla.GameComponent

class LifeLabel(warrior: Warrior, placement: Orientation = Orientation.EAST) extends RichGameComponent[PhantasyScene]{
  
    def lifeLabelAppearance = LabelUtils.lifeLabel(warrior.life.toString())
    var warriorLabelAppearance = LabelUtils.warriorNameLabel(warrior.name)
  
    this.position = PhantasyApp.screenCenter + placement.versor * (PhantasyApp.SCREEN_WIDTH / 2) * 0.8 + (0, PhantasyApp.SCREEN_HEIGHT / 3) 
    val warriorLabelObject = new RichGameComponent[PhantasyScene] {
      this.position = LifeLabel.this.position + (Orientation.NORTH.versor * (lifeLabelAppearance.getHeight / 2 + 10)) 
    }
    
    
    override def render(g: Graphics2D) = {
      lifeLabelAppearance.render(this, g)
      warriorLabelAppearance.render(warriorLabelObject, g)
    }
}
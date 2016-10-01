package ar.pablitar.phantasy

import ar.pablitar.vainilla.commons.components.RichGameComponent
import com.uqbar.vainilla.appearances.Label
import java.awt.Font
import java.awt.Color
import ar.pablitar.vainilla.commons.math.TimedValue
import com.uqbar.vainilla.DeltaState
import java.awt.Graphics2D
import com.uqbar.vainilla.GameComponent
import ar.pablitar.vainilla.commons.math.InterpolatorKind
import ar.pablitar.vainilla.commons.math.Vector2D
import java.awt.AlphaComposite

class AttackFeedback(a: AttackResult) extends RichGameComponent[PhantasyScene] {
  
  val duration = 0.9
    
  val animationProgress = new TimedValue(duration, InterpolatorKind.EaseOutQuad)
  
  def t = animationProgress.currentValue
  
  def scale = 1 + 5 * t - t * t * 5
  def distanceOffset = Vector2D(t * 2, -5 * t + 8 * t * t * t) * 100
  def alpha = (1 - t).toFloat
  
  this.setAppearance(LabelUtils.damageText(text, color))
  this.position = (PhantasyApp.screenCenter)
  
  def text = {
    a match {
      case Hit(_, damage, _) => damage.toString()
      case Miss(_) => "Miss"
    }
  }
  
  def color = {
    a match {
      case Hit(_,_,false)  => Color.YELLOW
      case Hit(_,_,true)  => Color.RED
      case Miss(_) => Color.WHITE
    }
  }
  
  override def update(state: DeltaState) = {
    animationProgress.update(state.getDelta)
    if(animationProgress.isOverDuration) {
      this.destroy()
    }
  }
  
  override def render(g: Graphics2D) = {
    val beforeTransform = g.getTransform
    val beforeComposite = g.getComposite
    g.translate(getX + distanceOffset.x1, getY + distanceOffset.x2)
    g.scale(scale, scale)
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha))
    this.getAppearance.render(new GameComponent(), g)
    g.setComposite(beforeComposite)
    g.setTransform(beforeTransform)
  }
}
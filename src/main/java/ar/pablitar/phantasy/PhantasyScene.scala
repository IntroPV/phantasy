package ar.pablitar.phantasy

import com.uqbar.vainilla.GameScene
import ar.pablitar.vainilla.appearances.PlainBackground
import java.awt.Color

class PhantasyScene extends GameScene{
  this.addComponent(new Combat(new Warrior("hero"), new Warrior("villain")))
  this.addComponent(new PlainBackground(Color.DARK_GRAY))

  def onAttack(result: AttackResult) = {
    this.addComponent(new AttackFeedback(result))
  }
}
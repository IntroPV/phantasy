package ar.pablitar.phantasy

import com.uqbar.vainilla.GameScene
import ar.pablitar.vainilla.appearances.PlainBackground
import java.awt.Color
import ar.pablitar.vainilla.commons.math.Orientation

class PhantasyScene extends GameScene{
  val hero = new Warrior("hero")
  val villain = new Warrior("villain")
  this.addComponent(new Combat(hero,villain))
  this.addComponent(new PlainBackground(Color.DARK_GRAY))
  this.addComponent(new LifeLabel(hero))
  this.addComponent(new LifeLabel(villain, Orientation.WEST))

  def onAttack(result: AttackResult) = {
    this.addComponent(new AttackFeedback(result))
  }
}
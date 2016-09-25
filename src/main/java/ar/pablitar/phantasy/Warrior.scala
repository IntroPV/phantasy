package ar.pablitar.phantasy

import scala.util.Random

class Warrior(val name: String) {

  def attack(defender: Warrior): AttackResult = {
    val atk = Attack(this, defender)
    if (Random.nextDouble() > 0.1)
      Hit(atk, this.damageAgainst(defender))
    else
      Miss(atk)
  }

  def damageAgainst(defender: Warrior) = {
    10
  }
}
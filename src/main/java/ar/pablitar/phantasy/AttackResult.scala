package ar.pablitar.phantasy

case class Attack(attacker: Warrior, defender: Warrior)

trait AttackResult {
  def attack: Attack
}

case class Hit(attack: Attack, damage: Int, critical: Boolean = false) extends AttackResult
case class Miss(attack: Attack) extends AttackResult
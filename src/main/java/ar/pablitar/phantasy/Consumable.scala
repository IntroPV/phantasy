package ar.pablitar.phantasy

abstract class Consumable(val cost: Double) {
  def effect(user: Warrior, opponent: Warrior): Unit
}
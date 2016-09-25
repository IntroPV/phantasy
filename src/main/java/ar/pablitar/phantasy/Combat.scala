package ar.pablitar.phantasy

import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key

class Combat(attacker: Warrior, defender: Warrior) extends GameComponent[PhantasyScene] {
  
  override def update(state: DeltaState) = {
    if(state.isKeyPressed(Key.SPACE)) {
      this.getScene.onAttack(attacker.attack(defender))
    }
  }
}
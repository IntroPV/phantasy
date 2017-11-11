package ar.pablitar.phantasy

import scala.util.Random

import Stat._
import Attribute._
import scala.collection.mutable.ArrayBuffer
import org.apache.commons.math3.distribution.NormalDistribution

trait Stat {
  def calculateBase(war: Warrior): Double
}

object Stat {
  case object HitRatio extends Stat {
    def calculateBase(war: Warrior): Double = {
      war.baseHit + (war.attributes(Dexterity) / 100) + (war.attributes(Agility) / 200)
    }
  }

  case object Evasion extends Stat {
    def calculateBase(war: Warrior): Double = {
      (war.attributes(Agility) * 1.5 + war.attributes(Luck) / 3) / 100
    }
  }
  case object MinDamage extends Stat {
    def calculateBase(war: Warrior): Double = {
      //Ahora hacemos que dependa del arma
      val dex = war.attributes(Dexterity)
      val str = war.attributes(Strength)
      war.baseMinDamage + (dex * dex / 2 + str)
    }
  }
  case object MaxDamage extends Stat {
    def calculateBase(war: Warrior): Double = {
      val str = war.attributes(Strength)
      val minDamage = war.calculate(MinDamage)
      war.baseMaxDamage + (str * str + war.attributes(Luck) / 4).max(minDamage)
    }
  }
  case object Defense extends Stat {
    def calculateBase(war: Warrior): Double = {
      war.baseDefense * ((war.attributes(Constitution) / 10
        + war.attributes(Agility) / 20 + war.attributes(Agility) / 30) / 3)
    }
  }

  case object CriticalRatio extends Stat {
    def calculateBase(war: Warrior): Double = {
      (war.attributes(Luck) + war.attributes(Agility) / 5) / 100
    }
  }

  case object MaxLife extends Stat {
    def calculateBase(war: Warrior): Double = {
      val ct = war.attributes(Constitution)
      ct * ct * 10 + war.attributes(Strength) * 10
    }
  }

  val allStats = Seq(HitRatio, MinDamage, MaxDamage, Defense, Evasion, CriticalRatio, MaxLife)
}

trait Attribute

object Attribute {
  case object Strength extends Attribute
  case object Dexterity extends Attribute
  case object Agility extends Attribute
  case object Constitution extends Attribute
  case object Luck extends Attribute
}

class Warrior(val name: String) {

  var level = 1

  val baseAttributes = Map[Attribute, Double](
    Strength -> (Random.nextInt(10) + 10),
    Dexterity -> (Random.nextInt(10) + 10),
    Agility -> (Random.nextInt(10) + 10),
    Constitution -> (Random.nextInt(10) + 10),
    Luck -> (Random.nextInt(10) + 10))

  println(name + ": " + baseAttributes)

  val consumables = ArrayBuffer.empty[Consumable]

  var life = calculate(MaxLife)

  allStats.foreach { s =>
    println(s"\t$s -> ${calculate(s)}")
  }

  //La idea es que los atributos finales sean calculados en función de, por ejemplo, el inventario
  def attributes = baseAttributes.map {
    case (attribute, value) => (attribute, value)
  }

  def attack(defender: Warrior): AttackResult = {
    val atk = Attack(this, defender)
    if (Random.nextDouble() < this.calculate(CriticalRatio))
      Hit(atk, this.damageAgainst(defender, true), true)
    else if (Random.nextDouble() < this.hitProbabilityAgainst(defender))
      Hit(atk, this.damageAgainst(defender))
    else
      Miss(atk)
  }

  def damageAgainst(defender: Warrior, critical: Boolean = false) = {
    def minDamage = calculate(MinDamage)
    def maxDamage = calculate(MaxDamage)

    val mean = (maxDamage + minDamage) / 2

    val normalDistribution = new NormalDistribution(mean, mean / 10)

    //    val thisHitDamage = (Random.nextDouble() * (maxDamage - minDamage) + minDamage) * (if (critical) 2 else 1)

    val thisHitDamage = normalDistribution.sample() * (if (critical) 2 else 1)

    (thisHitDamage - defender.calculate(Defense)).max(0).toInt
  }

  def hitProbabilityAgainst(defender: Warrior) = {
    this.hitRatio - defender.evasion
  }

  def hitRatio = {
    calculate(HitRatio)
  }

  def calculate(stat: Stat) = {
    this.applyBonuses(stat, stat.calculateBase(this))
  }

  def evasion = {
    calculate(Evasion)
  }

  //Cada stat podrá tener un bonus también, en función del equipment
  def applyBonuses(stat: Stat, base: Double) = {
    base
  }

  //Todos estos dependerían del inventario
  def baseHit = {
    0.9
  }

  def baseMinDamage = {
    10
  }

  def baseMaxDamage = {
    20
  }

  def baseDefense = {
    8
  }

  def receive(hit: Hit) = {
    this.life = (this.life - hit.damage).max(0)
  }
}
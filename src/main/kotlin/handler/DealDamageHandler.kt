package org.example.handler

import org.example.Command.DealDamage
import org.example.Event.MoveEvent
import org.example.Event.MoveEventDamage

class DealDamageHandler: Handler<DealDamage> {
    override fun handle(command: DealDamage): MoveEvent {
        val damage = damageTaken(command.damage, command.attacker.atk.actual, command.attacker.def.actual)
        command.target.hp -= damage;
        println()
        println(" it dealt $damage, now ${command.target.name} has ${command.target.hp} HP")
        return MoveEventDamage(damage)
    }
}

fun damageTaken(damage: Int, atk: Int, def: Int): Int{
  val damageFloat = damage.toFloat()
  val atkFloat = atk.toFloat()
  val defFloat = def.toFloat()
  val dealt = damageFloat * (atkFloat/defFloat)
  return dealt.toInt()
}

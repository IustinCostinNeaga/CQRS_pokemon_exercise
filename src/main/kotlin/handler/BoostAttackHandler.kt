package org.example.handler

import org.example.Command.BoostAttack
import org.example.Event.MoveEvent
import org.example.Event.MoveEventError
import org.example.Event.MoveEventStatChange

class BoostAttackHandler: Handler<BoostAttack> {
    override fun handle(command: BoostAttack): MoveEvent {
        return if(command.target.atk.statBoost != 6){
            command.target.atk.statBoost++
            command.target.atk.updateStat()
            println("${command.target.name} attack was boosted")
            MoveEventStatChange(command.target.atk.statBoost++)
        }
        else MoveEventError("ATK already at +6")
    }
}

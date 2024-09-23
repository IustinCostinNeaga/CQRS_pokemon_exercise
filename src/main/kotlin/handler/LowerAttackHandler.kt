package org.example.handler

import org.example.Command.LowerAttack
import org.example.Event.MoveEvent
import org.example.Event.MoveEventError
import org.example.Event.MoveEventStatChange

class LowerAttackHandler: Handler<LowerAttack> {
    override fun handle(command: LowerAttack): MoveEvent {
        return if(command.target.atk.statBoost != -6){
            command.target.atk.statBoost--
            command.target.atk.updateStat()
            println("${command.target.name} attack was reduced")
            MoveEventStatChange(command.target.atk.statBoost)
        }
        else MoveEventError("ATK already at -6")
    }
}
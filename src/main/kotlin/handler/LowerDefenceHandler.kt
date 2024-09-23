package org.example.handler

import org.example.Command.LowerDefence
import org.example.Event.MoveEvent
import org.example.Event.MoveEventError
import org.example.Event.MoveEventStatChange

class LowerDefenceHandler: Handler<LowerDefence> {
    override fun handle(command: LowerDefence): MoveEvent {
        return if(command.target.def.statBoost != -6){
            command.target.def.statBoost--
            command.target.def.updateStat()
            println("${command.target.name} defence was reduced")
            MoveEventStatChange(command.target.def.statBoost)
        }
        else MoveEventError("DEF already at -6")
    }
}
package org.example.handler

import org.example.Command.BoostDefence
import org.example.Event.MoveEvent
import org.example.Event.MoveEventError
import org.example.Event.MoveEventStatChange

class BoostDefenceHandler: Handler<BoostDefence> {
    override fun handle(command: BoostDefence): MoveEvent {
        return if(command.target.def.statBoost != 6){
            command.target.def.statBoost++
            command.target.def.updateStat()
            println("${command.target.name} defence was boosted")
            MoveEventStatChange(command.target.def.statBoost)
        }
        else MoveEventError("DEF already at +6")
    }
}
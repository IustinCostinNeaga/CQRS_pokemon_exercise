package org.example

import org.example.Command.*
import org.example.Event.MoveEvent
import org.example.handler.*

interface CommandHandler {

    companion object{
        fun create(): PokemonAction {
            return PokemonAction()
        }
    }

    fun send(command: Command): MoveEvent
}

class PokemonAction: CommandHandler {
    override fun send(command: Command): MoveEvent {
        return when(command){
            is DealDamage -> DealDamageHandler().handle(command)
            is BoostAttack -> BoostAttackHandler().handle(command)
            is BoostDefence -> BoostDefenceHandler().handle(command)
            is LowerAttack -> LowerAttackHandler().handle(command)
            is LowerDefence -> LowerDefenceHandler().handle(command)
            is SetStatus -> SetStatusHandler().handle(command)
        }
    }
}
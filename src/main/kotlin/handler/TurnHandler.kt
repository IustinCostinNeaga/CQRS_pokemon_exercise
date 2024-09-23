package org.example.handler

import org.example.CommandHandler
import org.example.Event.KeepFightingEvent
import org.example.Event.MoveEventDamage
import org.example.Event.PokemonDefeatedEvent
import org.example.Event.TurnEvent
import org.example.Pokemon

class TurnHandler(private val commandHandler: CommandHandler) {
    fun handleMove(firstPokemon: Pokemon, firstMoveName: String, secondPokemon: Pokemon, secondMoveName: String): TurnEvent {
        firstPokemon
            .useMove(firstMoveName, secondPokemon).forEach{ command ->
            commandHandler.send(command)
                .let {
                    when(it){
                        is MoveEventDamage -> if (pokemonIsDefeated(secondPokemon)) return PokemonDefeatedEvent(secondPokemon)
                        else -> Unit
                    }
                }
        }
        secondPokemon
            .useMove(secondMoveName, firstPokemon).forEach{ command ->
                commandHandler.send(command)
                    .let {
                        when(it){
                            is MoveEventDamage -> if (pokemonIsDefeated(firstPokemon)) return PokemonDefeatedEvent(firstPokemon)
                            else -> Unit
                        }
                    }
            }

        return KeepFightingEvent("Your pokemon \n $firstPokemon \n Enemy Pokemon \n $secondPokemon")
    }
}

fun pokemonIsDefeated(pokemon: Pokemon): Boolean{
    return pokemon.hp <= 0
}

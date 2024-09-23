package org.example.Event

import org.example.Pokemon

sealed interface TurnEvent

data class PokemonDefeatedEvent(
    val pokemon: Pokemon
): TurnEvent

data class KeepFightingEvent(
  val message: String
) : TurnEvent

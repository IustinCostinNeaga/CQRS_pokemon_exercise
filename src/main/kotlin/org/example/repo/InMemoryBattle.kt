package org.example.org.example.repo

import io.vertx.core.json.JsonObject
import org.example.Pokemon
import org.example.repo.InMemoryPokemon
import java.util.UUID

class InMemoryBattle() {

  var battles = listOf<Battle>()

  fun add(firstPokemon: Pokemon, secondPokemon: Pokemon): UUID {
    return Battle(firstPokemon = firstPokemon, secondPokemon = secondPokemon).let {
      battles = battles + it
      it.id
    }
  }

  fun getAll(): List<Battle> {
    return battles
  }

  fun getBy(id: UUID): Battle {
    return battles.first { id == it.id }
  }

  fun remove(id: UUID) {
    battles = battles.filter { id != it.id }
  }


  data class Battle(
    val id: UUID = UUID.randomUUID(),
    val firstPokemon: Pokemon,
    val secondPokemon: Pokemon,
    val turnPassed: Int = 0
  )
}

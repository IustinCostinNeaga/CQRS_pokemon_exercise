package org.example.repo

import org.example.Pokemon

class InMemoryPokemon {

  private var pokemons = emptyMap<String, Pokemon>()

  fun add(pokemon: Pokemon){
    pokemons = pokemons + (pokemon.name to pokemon)
  }

  fun get(name: String): Pokemon?{
    return pokemons[name]
  }

  fun getAll(): List<Pokemon>{
    return pokemons.map { it.value }
  }
}

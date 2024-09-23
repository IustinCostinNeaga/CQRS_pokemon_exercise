package handler

import io.mockk.MockK
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.example.Command.Damage
import org.example.Command.DealDamage
import org.example.CommandHandler
import org.example.Event.KeepFightingEvent
import org.example.Event.MoveEventDamage
import org.example.Event.PokemonDefeatedEvent
import org.example.Move
import org.example.Pokemon
import org.example.Stat
import org.example.handler.TurnHandler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TurnHandlerTest {
  val commandHandler: CommandHandler = mockk()
  val turnHandler = TurnHandler(commandHandler)
  lateinit var myPokemon: Pokemon
  lateinit var enemyPokemon: Pokemon

  @BeforeEach
  fun setUp() {
    myPokemon = yourPokemon()
    enemyPokemon = enemyPokemon()
    myPokemon.fightingAgainst = enemyPokemon
    enemyPokemon.fightingAgainst = myPokemon
    myPokemon.moves = pokemonMoves(myPokemon)
    enemyPokemon.moves = pokemonMoves(enemyPokemon)
  }

  @Test
  fun `should return that enemyPokemon is defeated`() {
    every { commandHandler.send(any<DealDamage>())}.answers {
      enemyPokemon.hp = 0
      MoveEventDamage(100)
    }

    val result = turnHandler.handleMove(myPokemon, "Attacco", enemyPokemon, "Attacco")

    assertThat(result).isEqualTo(PokemonDefeatedEvent(enemyPokemon))

    verify { commandHandler.send(match { it is DealDamage && it.attacker == myPokemon && it.target == enemyPokemon.copy(hp = 0) }) }
    verify(exactly = 0) { commandHandler.send(match { it is DealDamage && it.attacker != myPokemon }) }
  }

  @Test
  fun `should return that myPokemon is defeated`() {
    every { commandHandler.send(any<DealDamage>()) }.answers {
      myPokemon.hp = 0
      MoveEventDamage(100)
    }

    val result = turnHandler.handleMove(myPokemon, "Attacco", enemyPokemon, "Attacco")

    assertThat(result).isEqualTo(PokemonDefeatedEvent(myPokemon))

    verify { commandHandler.send(match { it is DealDamage && it.attacker == myPokemon && it.target == enemyPokemon }) }
    verify { commandHandler.send(match { it is DealDamage && it.target == myPokemon.copy(hp = 0) && it.attacker == enemyPokemon }) }
  }

  @Test
  fun `should return that neither Pokemon is defeated`() {
    every { commandHandler.send(any<DealDamage>()) }.answers {
      MoveEventDamage(1)
    }

    val result = turnHandler.handleMove(myPokemon, "Attacco", enemyPokemon, "Attacco")

    assertThat(result).isEqualTo(KeepFightingEvent)

    verify { commandHandler.send(match { it is DealDamage && it.attacker == myPokemon && it.target == enemyPokemon }) }
    verify { commandHandler.send(match { it is DealDamage && it.target == myPokemon && it.attacker == enemyPokemon }) }
  }
}


fun yourPokemon(): Pokemon {
  return Pokemon(
    name = "Charmander",
    hp = 100,
    atk = Stat("Attack", 10, 10, 0),
    def = Stat("Defence", 10, 10, 0),
    status = null,
    moves = emptyList(),
    fightingAgainst = null
  )
}

fun enemyPokemon(): Pokemon {
  return Pokemon(
    name = "Bulbasaur",
    hp = 100,
    atk = Stat("Attack", 20, 20, 0),
    def = Stat("Defence", 5, 5, 0),
    status = null,
    moves = emptyList(),
    fightingAgainst = null
  )
}

fun pokemonMoves(pokemon: Pokemon): List<Move> {
  return listOf(
    Move(
      "Attacco",
      listOf(Damage(10))
    )
  )
}

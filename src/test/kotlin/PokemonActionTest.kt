import org.example.*
import org.example.Command.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PokemonActionTest {

    private lateinit var yourPokemon: Pokemon
    private lateinit var enemyPokemon: Pokemon
    private val commandHandler = CommandHandler.create()


    @BeforeEach
    fun setUp() {
        yourPokemon = yourPokemon()
        enemyPokemon = enemyPokemon()
        yourPokemon.fightingAgainst = enemyPokemon
        enemyPokemon.fightingAgainst = yourPokemon
    }

    @Test
    fun `should damage a Pokemon`() {
        val attack = yourPokemon.useMove("Attacco")

        attack.forEach{ command ->
            commandHandler.send(command)
        }

        println(yourPokemon)
        println(enemyPokemon)
    }

    @Test
    fun `should boost own atk`() {
        val attack = yourPokemon.useMove("Aumenta Attacco")

        attack.forEach{ command ->
            commandHandler.send(command)
        }

        println(yourPokemon)
        println(enemyPokemon)
    }

    @Test
    fun `should lower  enemy atk`() {
        val attack = yourPokemon.useMove("Diminuisci Attacco")

        attack.forEach{ command ->
            commandHandler.send(command)
        }

        println(yourPokemon)
        println(enemyPokemon)
    }

    @Test
    fun `should boost own def`() {
        val attack = yourPokemon.useMove("Aumenta Difesa")

        attack.forEach{ command ->
            commandHandler.send(command)
        }

        println(yourPokemon)
        println(enemyPokemon)
    }

    @Test
    fun `should lower enemy def`() {
        val attack = yourPokemon.useMove("Diminuisci Difesa")

        attack.forEach{ command ->
            commandHandler.send(command)
        }

        println(yourPokemon)
        println(enemyPokemon)
    }

    @Test
    fun `should change enemy status`() {
        val attack = yourPokemon.useMove("Brucia Nemico")

        attack.forEach{ command ->
            commandHandler.send(command)
        }

        println(yourPokemon)
        println(enemyPokemon)
    }

    fun yourPokemon(): Pokemon {
        return Pokemon(
            name = "Charmander",
            hp = 100,
            atk = Stat("Attack", 10, 10, 0),
            def = Stat("Defence", 10, 10, 0),
            status = null,
            moves = pokemonMoves(),
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
            moves = pokemonMoves(),
            fightingAgainst = null
        )
    }

    fun pokemonMoves(): List<Move>{
        return listOf(
            Move(
                "Attacco",
                listOf(Damage(10))
            ),
            Move(
                "Aumenta Attacco",
                listOf(AttackUp)
            ),
            Move(
                "Diminuisci Attacco",
                listOf(AttackDown)
            ),
            Move(
                "Aumenta Difesa",
                listOf(DefenceUp)
            ),
            Move(
                "Diminuisci Difesa",
                listOf(DefenceDown)
            ),
            Move(
                "Brucia Nemico",
                listOf(StatusSetting(Status.BURN))
            )

        )
    }
}

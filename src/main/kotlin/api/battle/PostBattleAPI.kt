package org.example.api.battle

import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.example.Command.Damage
import org.example.Command.MoveType
import org.example.Move
import org.example.Pokemon
import org.example.Stat
import org.example.org.example.repo.InMemoryBattle
import org.example.repo.InMemoryPokemon

class PostBattleAPI(
  router: Router,
  private val inMemoryPokemon: InMemoryPokemon,
  private val inMemoryBattle: InMemoryBattle
) : Handler<RoutingContext> {

  init {
    router
      .post("/api/battle")
      .handler(BodyHandler.create())
      .handler(this)
  }

  override fun handle(context: RoutingContext) {
    val body = context.body().asJsonObject()
    val firstPokemon = inMemoryPokemon.get(body.getString("yourPokemon")) ?: missingNr()
    val secondPokemon = inMemoryPokemon.get(body.getString("enemyPokemon")) ?: missingNr()

    val id = inMemoryBattle.add(firstPokemon, secondPokemon)
    context
      .response()
      .send(id.toString())
  }
}

private fun missingNr(): Pokemon {
  return Pokemon(
    "MissingNr",
    1,
    Stat("atk", 1, 1, 1),
    Stat("def", 1, 1, 1),
    moves = listOf(
      Move(
        "Attacco scarso",
        "Un attacco scarso da 1 danno",
        listOf(Damage(1))
      )
    )
  )
}

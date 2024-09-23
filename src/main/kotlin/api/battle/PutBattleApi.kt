package org.example.api.battle

import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.example.Event.KeepFightingEvent
import org.example.Event.PokemonDefeatedEvent
import org.example.handler.TurnHandler
import org.example.org.example.repo.InMemoryBattle
import java.util.*

class PutBattleApi (router: Router, private val inMemoryBattle: InMemoryBattle, private val turnHandler: TurnHandler) : Handler<RoutingContext> {

  init {
    router
      .put("/api/battle")
      .handler(BodyHandler.create())
      .handler(this)
  }

  override fun handle(context: RoutingContext) {
    val body = context.body().asJsonObject()
    val id = UUID.fromString(body.getString("id"))
    val battle = inMemoryBattle.getBy(id)


    val firstPokemonMove = body.getString("move")
    val secondPokemonMove = battle.secondPokemon.moves.first().name

    val turnEvent = turnHandler.handleMove(battle.firstPokemon, firstPokemonMove, battle.secondPokemon, secondPokemonMove)

    when(turnEvent){
      is KeepFightingEvent -> context.response().send(turnEvent.message)
      is PokemonDefeatedEvent -> {
        inMemoryBattle.remove(id)
        context.response().setStatusCode(201).send("${turnEvent.pokemon.name} è stato sconfitto. Battaglia finita")
      }
    }

  }
}

package org.example.api.pokemons

import io.vertx.core.Handler
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.example.Pokemon
import org.example.repo.InMemoryPokemon

class GetPokemonApi(router: Router, private val inMemoryPokemon: InMemoryPokemon) : Handler<RoutingContext> {

  init {
    router
      .get("/api/pokemon")
      .handler(BodyHandler.create())
      .handler(this)
  }

  override fun handle(context: RoutingContext) {
    val name = context.queryParam("name").firstOrNull()
    if (name != null) {
      inMemoryPokemon
        .get(name)
        .let {
          context
            .response()
            .send(JsonObject.mapFrom(it).toString())
        }
    } else {
      inMemoryPokemon
        .getAll()
        .let {
          context
            .response()
            .send(JsonObject.mapFrom(Response(it)).toString())
        }
    }
  }
}

private data class Response(
  val pokemons: List<Pokemon>
)

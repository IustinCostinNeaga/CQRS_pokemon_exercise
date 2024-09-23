package org.example.api.pokemons

import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.example.Pokemon
import org.example.repo.InMemoryPokemon

class PostPokemonApi(router: Router, private val inMemoryPokemon: InMemoryPokemon): Handler<RoutingContext> {

  init {
      router
        .post("/api/pokemon")
        .handler(BodyHandler.create())
        .handler(this)
  }

  override fun handle(context: RoutingContext) {
    val body = context.body().asJsonObject()
    val pokemon = Pokemon.fromJson(body)
    inMemoryPokemon.add(pokemon)
    context
      .response()
      .send()
  }
}

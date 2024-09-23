package org.example

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.Vertx.vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.CorsHandler
import org.example.api.battle.GetBattleApi
import org.example.api.battle.PostBattleAPI
import org.example.api.battle.PutBattleApi
import org.example.api.pokemons.GetPokemonApi
import org.example.api.pokemons.PostPokemonApi
import org.example.handler.TurnHandler
import org.example.org.example.repo.InMemoryBattle
import org.example.repo.InMemoryPokemon

fun main() {
  val vertx = vertx()
  vertx.deployVerticle(MainVerticle())
}

class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router = Router.router(vertx)
    router.route().handler(
      CorsHandler.create("*")
        .allowedMethod(io.vertx.core.http.HttpMethod.GET)
        .allowedMethod(io.vertx.core.http.HttpMethod.POST)
        .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
        .allowedHeader("Access-Control-Request-Method")
        .allowedHeader("Access-Control-Allow-Credentials")
        .allowedHeader("Access-Control-Allow-Origin")
        .allowedHeader("Access-Control-Allow-Headers")
        .allowedHeader("Content-Type"));

    val commandHandler = CommandHandler.create()
    val inMemoryPokemon = InMemoryPokemon()
    val inMemoryBattle = InMemoryBattle()
    val turnHandler = TurnHandler(commandHandler)

    PostPokemonApi(router, inMemoryPokemon)
    GetPokemonApi(router, inMemoryPokemon)
    PostBattleAPI(router, inMemoryPokemon, inMemoryBattle)
    GetBattleApi(router, inMemoryBattle)
    PutBattleApi(router, inMemoryBattle, turnHandler)

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(8080).onComplete { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8080")
        } else {
          startPromise.fail(http.cause());
        }
      }

  }
}

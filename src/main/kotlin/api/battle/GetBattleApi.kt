package org.example.api.battle

import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.example.org.example.repo.InMemoryBattle
import org.example.repo.InMemoryPokemon
import java.util.*

class GetBattleApi (router: Router, private val inMemoryBattle: InMemoryBattle) : Handler<RoutingContext> {

  init {
    router
      .get("/api/battle")
      .handler(BodyHandler.create())
      .handler(this)
  }

  override fun handle(context: RoutingContext) {
    val id = context.queryParam("id").firstOrNull()
    if (id != null) {
      inMemoryBattle
        .getBy(UUID.fromString(id))
        .let {
          context
            .response()
            .send(JsonObject.mapFrom(it).toString())
        }
    } else {
      inMemoryBattle
        .getAll()
        .let {
          context
            .response()
            .send(JsonObject.mapFrom(Response(it)).toString())
        }
    }
  }
}

data class Response(
  val battles: List<InMemoryBattle.Battle>
)

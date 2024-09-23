package org.example

import com.fasterxml.jackson.annotation.JsonIgnore
import io.vertx.core.json.JsonObject
import io.vertx.core.json.JsonArray
import org.example.Command.*
import kotlin.math.abs

data class Pokemon(
    val name: String,
    var hp: Int,
    val atk: Stat,
    val def: Stat,
    var status: Status? = null,
    var moves: List<Move> = emptyList(),
){
  companion object {
    fun fromJson(json: JsonObject): Pokemon {
      return Pokemon(
        name = json.getString("name"),
        hp = json.getInteger("hp"),
        atk = Stat.fromJson(json.getJsonObject("atk"), "atk"),
        def = Stat.fromJson(json.getJsonObject("def"), "def"),
        status = null,
        moves = mapMoves(json.getJsonArray("moves")),
      )
    }
  }

    fun useMove(moveName: String, adversary: Pokemon): List<Command>{
      println(moves)
      println()
      println(moveName)
        val move =  moves.find { it.name == moveName }
        return move!!.command.map {
          when (it){
            is Damage -> DealDamage(this, it.damage, adversary)
            is AttackDown -> LowerAttack(adversary)
            is AttackDownSelf -> LowerAttack(this)
            is AttackUp -> BoostAttack(this)
            is DefenceDown -> LowerDefence(adversary)
            is DefenceDownSelf -> LowerDefence(this)
            is DefenceUp -> BoostDefence(this)
            is StatusSetting -> SetStatus(adversary, it.status)
            is MoveError -> TODO()
          }
        }
    }

    override fun toString(): String {
        val string = """
            ------------------------------------------------------------------------------------------------------------
            Name: $name
            Hp: $hp
            Status: ${status?: "In salute"}
            Atk: ${atk.actual} (base: ${atk.base}, change: ${atk.statBoost})
            Def: ${def.actual} (base: ${def.base}, change: ${def.statBoost})
            Moves: ${moves.joinToString { it.name }}
            ------------------------------------------------------------------------------------------------------------
        """.trimIndent()
        return string
    }
}

data class Move(
    val name: String,
    val description: String,
    @JsonIgnore
    val command: List<MoveType>
)

data class Stat(
    val stat: String,
    val base: Int,
    var actual: Int,
    var statBoost: Int
){
    companion object {
      fun fromJson(json: JsonObject, name: String): Stat {
        return Stat(
          stat = name,
          base = json.getInteger("base"),
          actual = json.getInteger("base"),
          statBoost = 0
        )
      }
    }

    fun updateStat(){
        val adjustedStatFactor: Float = abs(statBoost) + 2f
        if(statBoost>=0)
            actual = (base.toFloat() * (adjustedStatFactor/2)).toInt()
        else
            actual = (base.toFloat() * (2/adjustedStatFactor)).toInt()


    }
}

enum class Status{
    BURN,
    FREEZE,
    PARALYSIS,
    POISONED,
    POISONED_BADLY,
    SLEEP
}

fun mapMoves(json: JsonArray): List<Move>{
  return json.map {
    val move = it as JsonObject
    Move(
      name = move.getString("name"),
      description = move.getString("description"),
      command = mapCommands(move.getJsonArray("command"))
    )
  }
}

fun mapCommands(json: JsonArray):List<MoveType>{
  return json.map {
    val command = it as JsonObject
    val type = command.getString("type")
    when (type) {
      "Damage" -> Damage(command.getInteger("damage"))
      "AtkDown" -> AttackDown
      "AtkDownSelf" -> AttackDownSelf
      "AtkUp" -> AttackUp
      "DefDown" -> DefenceDown
      "DefDownSelf" -> DefenceDownSelf
      "DefUp" -> DefenceUp
      "SetStatus" -> StatusSetting(Status.valueOf(command.getString("status")))
      else -> {MoveError}
    }
  }
}

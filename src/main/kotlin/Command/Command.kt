package org.example.Command

import org.example.Pokemon
import org.example.Status

sealed interface Command

data class DealDamage(
    val attacker: Pokemon,
    val damage: Int,
    val target: Pokemon
):Command

data class LowerAttack(
    val target: Pokemon,
): Command
data class BoostAttack(
    val target: Pokemon,
): Command
data class LowerDefence(
    val target: Pokemon,
): Command
data class BoostDefence(
    val target: Pokemon,
): Command

data class SetStatus(
    val target: Pokemon,
    val status: Status
): Command
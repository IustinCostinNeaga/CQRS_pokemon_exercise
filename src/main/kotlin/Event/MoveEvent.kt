package org.example.Event

import org.example.Status

sealed interface MoveEvent

data class MoveEventDamage(
    val damage: Int
): MoveEvent

data class MoveEventStatChange(
    val statBoost: Int
): MoveEvent

data class MoveEventStatusChange(
    val status: Status
): MoveEvent

data class MoveEventError(
    val error: String
): MoveEvent
package org.example.Command

import org.example.Move
import org.example.Pokemon
import org.example.Status

sealed interface MoveType

data class Damage(
  val damage: Int,
):MoveType

data object AttackDown : MoveType
data object AttackDownSelf : MoveType
data object AttackUp: MoveType
data object DefenceUp: MoveType
data object DefenceDown: MoveType
data object DefenceDownSelf: MoveType

data class StatusSetting(
  val status: Status
): MoveType

data object MoveError: MoveType

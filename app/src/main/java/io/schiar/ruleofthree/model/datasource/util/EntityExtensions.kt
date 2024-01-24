package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.model.datasource.database.CrossMultiplierEntity
import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierEntity
import io.schiar.ruleofthree.stringify

fun CrossMultiplierEntity.toCrossMultiplier(): CrossMultiplier {
    val pairStringList = unknownPosition.split(" ")
    return CrossMultiplier(
        id = id,
        valueAt00 = a,
        valueAt01 = b,
        valueAt10 = c,
        valueAt11 = d,
        unknownPosition = Pair(pairStringList[0].toInt(), pairStringList[1].toInt())
    )
}

fun CurrentCrossMultiplierEntity.toCrossMultiplier(): CrossMultiplier {
    val pairStringList = unknownPosition.split(" ")
    return CrossMultiplier(
        id = id,
        valueAt00 = a,
        valueAt01 = b,
        valueAt10 = c,
        valueAt11 = d,
        unknownPosition = Pair(pairStringList[0].toInt(), pairStringList[1].toInt())
    )
}

fun CrossMultiplierEntity.setID(id: Long): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        id = id,
        a = a,
        b = b,
        c = c,
        d = d,
        unknownPosition = unknownPosition,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}

fun CrossMultiplier.toCrossMultiplierEntity(): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        id = id,
        a = valueAt00,
        b = valueAt01,
        c = valueAt10,
        d = valueAt11,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

fun CrossMultiplier.toCurrentCrossMultiplierEntity(): CurrentCrossMultiplierEntity {
    return CurrentCrossMultiplierEntity(
        id = id,
        a = valueAt00,
        b = valueAt01,
        c = valueAt10,
        d = valueAt11,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

fun List<CrossMultiplierEntity>.toCrossMultipliers(): List<CrossMultiplier> {
    return map { it.toCrossMultiplier() }
}

fun CrossMultiplierEntity.timestamped(
    createdAt: Long = -1, modifiedAt: Long = -1
): CrossMultiplierEntity {
   return CrossMultiplierEntity(
       id = id,
       a = a,
       b = b,
       c = c,
       d = d,
       unknownPosition = unknownPosition,
       createdAt = if (createdAt != -1L) createdAt else this.createdAt,
       modifiedAt = if (modifiedAt != -1L) createdAt else this.createdAt
    )
}
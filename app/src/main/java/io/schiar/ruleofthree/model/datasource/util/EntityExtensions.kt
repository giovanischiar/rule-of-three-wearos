package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.model.datasource.database.CrossMultiplierEntity
import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierEntity

fun CrossMultiplierEntity.toCrossMultiplier(): CrossMultiplier {
    val pairStringList = unknownPosition.split(" ")
    return CrossMultiplier(
        id = id,
        values = arrayOf(
            arrayOf(Input(value = a), Input(value = b)),
            arrayOf(Input(value = c), Input(value = d))
        ),
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
        a = values[0][0].value,
        b = values[0][1].value,
        c = values[1][0].value,
        d = values[1][1].value,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

fun CrossMultiplier.toCurrentCrossMultiplierEntity(): CurrentCrossMultiplierEntity {
    return CurrentCrossMultiplierEntity(
        id = id,
        a = values[0][0].value,
        b = values[0][1].value,
        c = values[1][0].value,
        d = values[1][1].value,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

fun CurrentCrossMultiplierEntity.toCrossMultiplier(): CrossMultiplier {
    val pairStringList = unknownPosition.split(" ")
    return CrossMultiplier(
        id = id,
        values = arrayOf(
            arrayOf(Input(value = a), Input(value = b)),
            arrayOf(Input(value = c), Input(value = d))
        ),
        unknownPosition = Pair(pairStringList[0].toInt(), pairStringList[1].toInt())
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
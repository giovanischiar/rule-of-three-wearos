package io.schiar.ruleofthree.library.room

import io.schiar.ruleofthree.model.CrossMultiplier

fun CrossMultiplier.toCrossMultiplierEntity(): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        id = id,
        valueAt00 = valueAt00,
        valueAt01 = valueAt01,
        valueAt10 = valueAt10,
        valueAt11 = valueAt11,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

fun CrossMultiplierEntity.toCrossMultiplier(): CrossMultiplier {
    val pairStringList = unknownPosition.split(" ")
    return CrossMultiplier(
        id = id,
        valueAt00 = valueAt00,
        valueAt01 = valueAt01,
        valueAt10 = valueAt10,
        valueAt11 = valueAt11,
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
        valueAt00 = valueAt00,
        valueAt01 = valueAt01,
        valueAt10 = valueAt10,
        valueAt11 = valueAt11,
        unknownPosition = unknownPosition,
        createdAt = if (createdAt != -1L) createdAt else this.createdAt,
        modifiedAt = if (modifiedAt != -1L) createdAt else this.createdAt
    )
}

fun CurrentCrossMultiplierEntity.toCrossMultiplier(): CrossMultiplier {
    val pairStringList = unknownPosition.split(" ")
    return CrossMultiplier(
        id = id,
        valueAt00 = valueAt00,
        valueAt01 = valueAt01,
        valueAt10 = valueAt10,
        valueAt11 = valueAt11,
        unknownPosition = Pair(pairStringList[0].toInt(), pairStringList[1].toInt())
    )
}

fun CrossMultiplier.toCurrentCrossMultiplierEntity(): CurrentCrossMultiplierEntity {
    return CurrentCrossMultiplierEntity(
        id = id,
        valueAt00 = valueAt00,
        valueAt01 = valueAt01,
        valueAt10 = valueAt10,
        valueAt11 = valueAt11,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}
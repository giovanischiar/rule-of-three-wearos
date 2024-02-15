package io.schiar.ruleofthree.model.datasource.currentcrossmultiplier.requester

import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierEntity
import io.schiar.ruleofthree.model.CrossMultiplier

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
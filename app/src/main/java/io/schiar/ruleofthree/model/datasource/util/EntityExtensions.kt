package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.model.datasource.room.CrossMultiplierEntity

fun CrossMultiplierEntity.toModel(): CrossMultiplier {
    val pairStringList = unknownPosition?.split(" ") ?: listOf("1", "1")
    return CrossMultiplier(
        values = arrayOf(
            arrayOf(Input(value = a ?: ""), Input(value = b ?: "")),
            arrayOf(Input(value = c ?: ""), Input(value = d ?: ""))
        ),
        unknownPosition = Pair(pairStringList[0].toInt(), pairStringList[1].toInt())
    )
}

fun CrossMultiplier.toEntity(): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        a = values[0][0].value,
        b = values[0][1].value,
        c = values[1][0].value,
        d = values[1][1].value,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

fun CrossMultiplier.toEntity(id: Long): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        id = id,
        a = values[0][0].value,
        b = values[0][1].value,
        c = values[1][0].value,
        d = values[1][1].value,
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

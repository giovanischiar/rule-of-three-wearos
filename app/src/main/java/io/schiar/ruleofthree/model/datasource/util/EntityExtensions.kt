package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.datasource.room.CrossMultiplierEntity

fun CrossMultiplierEntity.toModel(): CrossMultiplier {
    val pairStringList = unknownPosition?.split(" ") ?: listOf("1", "1")
    return CrossMultiplier(
        a = a ?: "",
        b = b ?: "",
        c = c ?: "",
        result = result,
        unknownPosition = Pair(pairStringList[0].toInt(), pairStringList[1].toInt())
    )
}

fun CrossMultiplier.toEntity(): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        a = this.a().value,
        b = this.b().value,
        c = this.c().value,
        result = this.result(),
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

fun CrossMultiplier.toEntity(id: Long): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        id = id,
        a = this.a().value,
        b = this.b().value,
        c = this.c().value,
        result = this.result(),
        unknownPosition = "${unknownPosition.first} ${unknownPosition.second}"
    )
}

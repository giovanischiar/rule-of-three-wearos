package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.CrossMultiplier
import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.model.datasource.room.CrossMultiplierEntity

fun CrossMultiplierEntity.toModel(): CrossMultiplier {
    return CrossMultiplier(
        a = Input(value = a ?: ""),
        b = Input(value = b ?: ""),
        c = Input(value = c ?: ""),
        result = result
    )
}

fun CrossMultiplier.toEntity(): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        a = a.value,
        b = b.value,
        c = c.value,
        result = result
    )
}

fun CrossMultiplier.toEntity(id: Long): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        id = id,
        a = a.value,
        b = b.value,
        c = c.value,
        result = result
    )
}

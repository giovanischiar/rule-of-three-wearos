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
        a = values[0][0].value,
        b = values[0][1].value,
        c = values[1][0].value,
        result = values[1][1].value.toDoubleOrNull()
    )
}

fun CrossMultiplier.toEntity(id: Long): CrossMultiplierEntity {
    return CrossMultiplierEntity(
        id = id,
        a = values[0][0].value,
        b = values[0][1].value,
        c = values[1][0].value,
        result = values[1][1].value.toDoubleOrNull()
    )
}

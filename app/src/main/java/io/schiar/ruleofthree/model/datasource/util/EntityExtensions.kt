package io.schiar.ruleofthree.model.datasource.util

import io.schiar.ruleofthree.model.Input
import io.schiar.ruleofthree.model.Numbers
import io.schiar.ruleofthree.model.datasource.room.NumbersEntity

fun NumbersEntity.toModel(): Numbers {
    return Numbers(
        a = Input(value = a ?: ""),
        b = Input(value = b ?: ""),
        c = Input(value = c ?: ""),
        result = result
    )
}

fun Numbers.toEntity(): NumbersEntity {
    return NumbersEntity(
        a = a.value,
        b = b.value,
        c = c.value,
        result = result
    )
}

fun Numbers.toEntity(id: Long): NumbersEntity {
    return NumbersEntity(
        id = id,
        a = a.value,
        b = b.value,
        c = c.value,
        result = result
    )
}

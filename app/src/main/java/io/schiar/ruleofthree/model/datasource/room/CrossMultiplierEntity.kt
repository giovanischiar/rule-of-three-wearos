package io.schiar.ruleofthree.model.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CrossMultiplier")
data class CrossMultiplierEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val a: String?,
    val b: String?,
    val c: String?,
    val result: Double?
)
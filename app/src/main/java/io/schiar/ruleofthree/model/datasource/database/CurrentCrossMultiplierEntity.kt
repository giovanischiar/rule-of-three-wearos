package io.schiar.ruleofthree.model.datasource.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentCrossMultiplier")
data class CurrentCrossMultiplierEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1,
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val unknownPosition: String,
    var createdAt: Long = 0,
    var modifiedAt: Long = 0
)
package io.schiar.ruleofthree.library.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentCrossMultiplier")
data class CurrentCrossMultiplierEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1,
    val valueAt00: String,
    val valueAt01: String,
    val valueAt10: String,
    val valueAt11: String,
    val unknownPosition: String,
    var createdAt: Long = 0,
    var modifiedAt: Long = 0
)
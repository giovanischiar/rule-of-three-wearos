package io.schiar.ruleofthree.library.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PastCrossMultipliers")
data class CrossMultiplierEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val valueAt00: String,
    val valueAt01: String,
    val valueAt10: String,
    val valueAt11: String,
    val unknownPosition: String,
    var createdAt: Long = 0,
    var modifiedAt: Long = 0
)
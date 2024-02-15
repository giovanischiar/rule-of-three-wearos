package io.schiar.ruleofthree.library.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PastCrossMultipliersRoomDAO {
    @Insert
    fun insert(crossMultiplierEntity: CrossMultiplierEntity): Long

    @Query("SELECT * FROM PastCrossMultipliers ORDER BY createdAt DESC")
    fun selectFromPastCrossMultiplierOrderByCreatedAtDesc(): List<CrossMultiplierEntity>

    @Update
    fun update(crossMultiplierEntity: CrossMultiplierEntity)

    @Delete
    fun delete(crossMultiplierEntity: CrossMultiplierEntity)

    @Query("DELETE FROM PastCrossMultipliers")
    fun deleteFromPastCrossMultipliers()
}
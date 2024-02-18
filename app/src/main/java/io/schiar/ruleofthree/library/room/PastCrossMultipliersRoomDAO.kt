package io.schiar.ruleofthree.library.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PastCrossMultipliersRoomDAO {
    @Insert
    suspend fun insert(crossMultiplierEntity: CrossMultiplierEntity): Long

    @Query("SELECT * FROM PastCrossMultipliers ORDER BY createdAt DESC")
    fun selectFromPastCrossMultiplierOrderByCreatedAtDesc(): Flow<List<CrossMultiplierEntity>>

    @Query("SELECT * FROM PastCrossMultipliers WHERE id == :id LIMIT 1")
    suspend fun select(id: Long): CrossMultiplierEntity?

    @Update
    suspend fun update(crossMultiplierEntity: CrossMultiplierEntity)

    @Delete
    suspend fun delete(crossMultiplierEntity: CrossMultiplierEntity)

    @Query("DELETE FROM PastCrossMultipliers")
    suspend fun deleteFromPastCrossMultipliers()
}
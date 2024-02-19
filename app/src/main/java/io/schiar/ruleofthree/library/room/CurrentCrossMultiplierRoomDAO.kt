package io.schiar.ruleofthree.library.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentCrossMultiplierRoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity): Long

    @Update
    suspend fun update(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity)

    @Query("SELECT * from CurrentCrossMultiplier LIMIT 1")
    fun select(): Flow<CurrentCrossMultiplierEntity?>
}
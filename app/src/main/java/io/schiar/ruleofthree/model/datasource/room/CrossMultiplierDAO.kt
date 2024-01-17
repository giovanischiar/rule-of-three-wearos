package io.schiar.ruleofthree.model.datasource.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CrossMultiplierDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(crossMultiplierEntity: CrossMultiplierEntity): Long

    @Update
    fun update(crossMultiplierEntity: CrossMultiplierEntity)

    @Delete
    fun delete(crossMultiplierEntity: CrossMultiplierEntity)

    @Query("SELECT * FROM CrossMultiplier WHERE id != 1")
    fun selectAllPastCrossMultipliers(): List<CrossMultiplierEntity>

    @Query("SELECT * FROM CrossMultiplier WHERE id == 1")
    fun selectCurrentCrossMultiplier(): CrossMultiplierEntity?

    @Query("SELECT id FROM CrossMultiplier WHERE a == :a AND b == :b AND c == :c AND result == :result")
    fun selectHistoryItemID(a: String?, b: String?, c: String?, result: Double?): Long?
}
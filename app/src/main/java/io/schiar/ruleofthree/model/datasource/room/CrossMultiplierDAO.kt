package io.schiar.ruleofthree.model.datasource.room

import androidx.room.Dao
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

    @Query("SELECT * FROM CrossMultiplier WHERE id != 1")
    fun selectAllPastCrossMultipliers(): List<CrossMultiplierEntity>

    @Query("SELECT * FROM CrossMultiplier WHERE id == 1")
    fun selectCurrentCrossMultiplier(): CrossMultiplierEntity?

    @Query("DELETE FROM CrossMultiplier WHERE a == :a AND b == :b AND c == :c AND d == :d AND unknownPosition == :unknownPosition")
    fun deleteHistoryItem(a: String?, b: String?, c: String?, d: String?, unknownPosition: String?)

    @Query("DELETE FROM CrossMultiplier WHERE id != 1")
    fun deleteHistory()

    @Query("SELECT * FROM CrossMultiplier WHERE a == :a AND b == :b AND c == :c AND d == :d AND unknownPosition == :unknownPosition")
    fun selectCrossMultiplier(
        a: String?, b: String?, c: String?, d: String?, unknownPosition: String?
    ): CrossMultiplierEntity?
}
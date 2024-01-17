package io.schiar.ruleofthree.model.datasource.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NumbersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(numbersEntity: NumbersEntity): Long

    @Update
    fun update(numbersEntity: NumbersEntity)

    @Delete
    fun delete(numbersEntity: NumbersEntity)

    @Query("SELECT * FROM Numbers WHERE id != 1")
    fun selectAllPastNumbers(): List<NumbersEntity>

    @Query("SELECT * FROM Numbers WHERE id == 1")
    fun selectCurrentNumbers(): NumbersEntity?

    @Query("SELECT id FROM Numbers WHERE a == :a AND b == :b AND c == :c AND result == :result")
    fun selectHistoryItemID(a: String?, b: String?, c: String?, result: Double?): Long?
}
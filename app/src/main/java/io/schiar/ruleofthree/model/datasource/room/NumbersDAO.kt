package io.schiar.ruleofthree.model.datasource.room

import androidx.room.Dao
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

    @Query("SELECT * FROM Numbers WHERE id != 1")
    fun selectAllPastNumbers(): List<NumbersEntity>

    @Query("SELECT * FROM Numbers WHERE id == 1")
    fun selectCurrentNumbers(): NumbersEntity?
}
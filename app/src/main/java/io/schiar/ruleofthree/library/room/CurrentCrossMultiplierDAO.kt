package io.schiar.ruleofthree.library.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
abstract class CurrentCrossMultiplierDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity): Long

    fun insertWithTimestamp(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity) {
        insert(currentCrossMultiplierEntity.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    @Update
    abstract fun update(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity)

    fun updateWithTimestamp(currentCrossMultiplierEntity: CurrentCrossMultiplierEntity) {
        update(currentCrossMultiplierEntity.apply {
            modifiedAt = System.currentTimeMillis()
        })
    }

    @Query("SELECT * from CurrentCrossMultiplier LIMIT 1")
    abstract fun select(): CurrentCrossMultiplierEntity?
}
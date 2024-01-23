package io.schiar.ruleofthree.model.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.schiar.ruleofthree.model.datasource.util.setID
import io.schiar.ruleofthree.model.datasource.util.timestamped

@Dao
abstract class PastCrossMultipliersDAO {
    @Insert
    abstract fun insert(crossMultiplierEntity: CrossMultiplierEntity): Long

    fun insertTimestamped(crossMultiplierEntity: CrossMultiplierEntity): CrossMultiplierEntity {
        val crossMultiplierEntityToInsert = crossMultiplierEntity.timestamped(
            createdAt = System.currentTimeMillis(),
            modifiedAt = System.currentTimeMillis()
        )

        val crossMultiplierEntityToInsertID = insert(
            crossMultiplierEntity = crossMultiplierEntityToInsert
        )
        return crossMultiplierEntityToInsert.setID(id = crossMultiplierEntityToInsertID)
    }

    @Query("SELECT * FROM PastCrossMultipliers ORDER BY createdAt DESC")
    abstract fun selectFromPastCrossMultiplierOrderByCreatedAtDesc(): List<CrossMultiplierEntity>

    @Update
    abstract fun update(crossMultiplierEntity: CrossMultiplierEntity)

    fun updateCrossMultiplierEntityTimestamped(
        crossMultiplierEntityUpdated: CrossMultiplierEntity
    ) {
        update(
            crossMultiplierEntity = crossMultiplierEntityUpdated.timestamped(
                modifiedAt = System.currentTimeMillis()
            )
        )
    }

    @Update
    abstract fun delete(crossMultiplierEntity: CrossMultiplierEntity)

    @Query("DELETE FROM PastCrossMultipliers")
    abstract fun deleteFromPastCrossMultipliers()
}
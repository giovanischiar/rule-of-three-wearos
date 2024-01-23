package io.schiar.ruleofthree

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.schiar.ruleofthree.model.datasource.database.CrossMultiplierEntity
import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierDAO
import io.schiar.ruleofthree.model.datasource.database.CurrentCrossMultiplierEntity
import io.schiar.ruleofthree.model.datasource.database.PastCrossMultipliersDAO

@Database(
    entities = [CurrentCrossMultiplierEntity::class, CrossMultiplierEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RuleOfThreeDatabase : RoomDatabase() {
    abstract fun pastCrossMultipliersDAO(): PastCrossMultipliersDAO
    abstract fun currentCrossMultiplierDAO(): CurrentCrossMultiplierDAO

    companion object {
        @Volatile
        private var Instance: RuleOfThreeDatabase? = null

        fun getDatabase(context: Context): RuleOfThreeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = RuleOfThreeDatabase::class.java,
                    name = "location_database"
                ).fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}
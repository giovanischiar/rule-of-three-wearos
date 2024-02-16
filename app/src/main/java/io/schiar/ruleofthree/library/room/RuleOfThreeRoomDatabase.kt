package io.schiar.ruleofthree.library.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CurrentCrossMultiplierEntity::class, CrossMultiplierEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RuleOfThreeRoomDatabase : RoomDatabase() {
    abstract fun pastCrossMultipliersDAO(): PastCrossMultipliersRoomDAO
    abstract fun currentCrossMultiplierDAO(): CurrentCrossMultiplierRoomDAO

    companion object {
        @Volatile
        private var Instance: RuleOfThreeRoomDatabase? = null

        fun getDatabase(context: Context): RuleOfThreeRoomDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = RuleOfThreeRoomDatabase::class.java,
                    name = "location_database"
                ).fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}
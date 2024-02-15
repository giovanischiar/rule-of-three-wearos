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
abstract class RuleOfThreeDatabase : RoomDatabase() {
    abstract fun pastCrossMultipliersDAO(): PastCrossMultipliersRoomDAO
    abstract fun currentCrossMultiplierDAO(): CurrentCrossMultiplierRoomDAO

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
package io.schiar.ruleofthree.library.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomDAO
import io.schiar.ruleofthree.library.room.PastCrossMultipliersRoomDAO
import io.schiar.ruleofthree.library.room.RuleOfThreeRoomDatabase

@Module
@InstallIn(SingletonComponent::class)
object RoomDAOProviderModule {
    @Provides
    fun providePastCrossMultipliersRoomDAO(
        @ApplicationContext context: Context
    ): PastCrossMultipliersRoomDAO {
        return RuleOfThreeRoomDatabase.getDatabase(context).pastCrossMultipliersDAO()
    }

    @Provides
    fun provideCurrentCrossMultiplierRoomDAO(
        @ApplicationContext context: Context
    ): CurrentCrossMultiplierRoomDAO {
        return RuleOfThreeRoomDatabase.getDatabase(context).currentCrossMultiplierDAO()
    }
}
package io.schiar.ruleofthree

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomDataSource
import io.schiar.ruleofthree.library.room.PastCrossMultipliersRoomDataSource
import io.schiar.ruleofthree.library.room.RuleOfThreeRoomDatabase
import io.schiar.ruleofthree.model.repository.AppRepository
import io.schiar.ruleofthree.model.repository.CrossMultipliersCreatorRepository
import io.schiar.ruleofthree.model.repository.HistoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun providePastCrossMultipliersRoomDataSource(
        @ApplicationContext context: Context
    ): PastCrossMultipliersRoomDataSource {
        return PastCrossMultipliersRoomDataSource(
            RuleOfThreeRoomDatabase.getDatabase(context).pastCrossMultipliersDAO()
        )
    }

    @Provides
    @Singleton
    fun provideCurrentCrossMultiplierRoomDataSource(
        @ApplicationContext context: Context
    ): CurrentCrossMultiplierRoomDataSource {
        return CurrentCrossMultiplierRoomDataSource(
            RuleOfThreeRoomDatabase.getDatabase(context).currentCrossMultiplierDAO()
        )
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        pastCrossMultipliersRoomDataSource: PastCrossMultipliersRoomDataSource,
        currentCrossMultiplierRoomDataSource: CurrentCrossMultiplierRoomDataSource
    ): AppRepository {
        return AppRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersRoomDataSource,
            currentCrossMultiplierDataSource = currentCrossMultiplierRoomDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCrossMultipliersCreatorRepository(
        currentCrossMultiplierRoomDataSource: CurrentCrossMultiplierRoomDataSource
    ): CrossMultipliersCreatorRepository {
        return CrossMultipliersCreatorRepository(
            currentCrossMultiplierDataSource = currentCrossMultiplierRoomDataSource
        )
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(
        pastCrossMultipliersRoomDataSource: PastCrossMultipliersRoomDataSource,
        crossMultipliersCreatorRepository: CrossMultipliersCreatorRepository
    ): HistoryRepository {
        return HistoryRepository(
            pastCrossMultipliersDataSource = pastCrossMultipliersRoomDataSource,
            areTherePastCrossMultipliersListener = crossMultipliersCreatorRepository
        )
    }
}
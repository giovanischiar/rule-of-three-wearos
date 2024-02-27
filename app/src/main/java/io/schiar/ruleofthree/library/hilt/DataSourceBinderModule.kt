package io.schiar.ruleofthree.library.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.schiar.ruleofthree.library.room.CurrentCrossMultiplierRoomDataSource
import io.schiar.ruleofthree.library.room.PastCrossMultipliersRoomDataSource
import io.schiar.ruleofthree.model.datasource.CurrentCrossMultiplierDataSource
import io.schiar.ruleofthree.model.datasource.PastCrossMultipliersDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceBinderModule {
    @Binds
    abstract fun bindCurrentCrossMultiplierDataSource(
        currentCrossMultiplierRoomDataSource: CurrentCrossMultiplierRoomDataSource
    ): CurrentCrossMultiplierDataSource

    @Binds
    abstract fun bindPastCrossMultipliersDataSource(
        pastCrossMultipliersRoomDataSource: PastCrossMultipliersRoomDataSource
    ): PastCrossMultipliersDataSource
}
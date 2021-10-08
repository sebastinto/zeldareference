package com.tobianoapps.zeldareference.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.tobianoapps.zeldareference.api.models.BossAppearances
import com.tobianoapps.zeldareference.api.models.BossDungeons
import com.tobianoapps.zeldareference.api.models.Bosses
import com.tobianoapps.zeldareference.api.models.Games
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class MoshiModule {

    @Reusable
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    /*** GAMES ***/
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class GamesAdapterMoshi

    @GamesAdapterMoshi
    @Reusable
    @Provides
    fun provideGamesAdapter(
        moshi: Moshi
    ): JsonAdapter<Games> {
        return moshi.adapter(Games::class.java)
    }

    /*** BOSSES ***/
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class BossesAdapterMoshi

    @BossesAdapterMoshi
    @Reusable
    @Provides
    fun provideBossesAdapter(
        moshi: Moshi
    ): JsonAdapter<Bosses> {
        return moshi.adapter(Bosses::class.java)
    }

    /*** APPEARANCE ***/
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class BossAppearancesAdapterMoshi

    @BossAppearancesAdapterMoshi
    @Reusable
    @Provides
    fun provideAppearancesAdapter(
        moshi: Moshi
    ): JsonAdapter<BossAppearances> {
        return moshi.adapter(BossAppearances::class.java)
    }

    /*** DUNGEON ***/
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class BossDungeonAdapterMoshi

    @BossDungeonAdapterMoshi
    @Reusable
    @Provides
    fun provideBossDungeonAdapter(
        moshi: Moshi
    ): JsonAdapter<BossDungeons> {
        return moshi.adapter(BossDungeons::class.java)
    }
}

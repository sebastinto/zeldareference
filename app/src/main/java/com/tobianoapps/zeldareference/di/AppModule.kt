package com.tobianoapps.zeldareference.di

import com.tobianoapps.zeldareference.ZeldaApp
import com.tobianoapps.zeldareference.api.ZeldaApi
import com.tobianoapps.zeldareference.di.CoroutineModule.IoDispatcher
import com.tobianoapps.zeldareference.repository.ZeldaRepository
import com.tobianoapps.zeldareference.repository.ZeldaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * This class defines constructors for app-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication() = ZeldaApp()


    @Singleton
    @Provides
    fun provideZeldaRepository(
        zeldaApi: ZeldaApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ZeldaRepository {
        return ZeldaRepositoryImpl(zeldaApi, ioDispatcher)
    }
}

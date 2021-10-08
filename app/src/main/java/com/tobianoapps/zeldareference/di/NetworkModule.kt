package com.tobianoapps.zeldareference.di


import com.tobianoapps.zeldareference.BuildConfig
import com.tobianoapps.zeldareference.api.ZeldaApi
import com.tobianoapps.zeldareference.api.models.Bosses
import com.tobianoapps.zeldareference.api.models.Games
import com.tobianoapps.zeldareference.util.Extensions.getPath
import com.tobianoapps.zeldareference.util.networking.ErrorInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

const val ZELDA_BASE_URL = "https://zelda-api.apius.cc/api/"
const val CONNECT_TIMEOUT = 10L

/**
 * This class defines constructors for network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    /**
     * This function allows override the base url for testing.
     */
    protected open fun baseUrl() = ZELDA_BASE_URL.toHttpUrl()

    @Singleton
    @Provides
    fun provideZeldaApi(retrofit: Retrofit): ZeldaApi {
        return retrofit.create(ZeldaApi::class.java)
    }

    @Reusable
    @Provides
    fun provideBaseUrl() = ZELDA_BASE_URL

    @Reusable
    @Provides
    open fun provideOkHttpClient(): OkHttpClient {

        // Debugging
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Errors
        val errorInterceptor = ErrorInterceptor()

        // Prevent creation of multiple instances
        val okHttpClient = OkHttpClient()

        // Add logging interceptor for debug builds only
        return if (BuildConfig.DEBUG){
            okHttpClient.newBuilder()
                .addInterceptor(errorInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build()
        } else {
            okHttpClient.newBuilder()
                .addInterceptor(errorInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) // tells retrofit how to parse the response json / xml
            .baseUrl(baseUrl())
            .client(okHttpClient)
            .build()
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class GamesApiPath

    @GamesApiPath
    @Reusable
    @Provides
    fun provideGamesApiPath(): String {
        return Games::class.java.getPath()
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class BossesApiPath

    @BossesApiPath
    @Reusable
    @Provides
    fun provideBossesApiPath(): String {
        return Bosses::class.java.getPath()
    }
}

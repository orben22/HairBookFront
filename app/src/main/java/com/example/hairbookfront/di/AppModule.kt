package com.example.hairbookfront.di

import android.app.Application
import com.example.hairbookfront.data.manager.LocalUserManagerImpl
import com.example.hairbookfront.data.remote.ApiService
import com.example.hairbookfront.data.remote.HairBookDataSource
import com.example.hairbookfront.data.remote.HairBookDataSourceImpl
import com.example.hairbookfront.domain.manager.LocalUserManager
import com.example.hairbookfront.domain.repository.ApiRepository
import com.example.hairbookfront.domain.usecases.app_entry.AppEntryUseCases
import com.example.hairbookfront.domain.usecases.app_entry.ReadAppEntry
import com.example.hairbookfront.domain.usecases.app_entry.SaveAppEntry
import com.example.hairbookfront.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager {
        return LocalUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager): AppEntryUseCases {
        return AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )
    }


    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            // Logs request and response lines and their respective headers and bodies (if present).
            level = HttpLoggingInterceptor.Level.BODY
        }
        // add the interceptor to OkHttpClient
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDataSource(apiService: ApiService): HairBookDataSource {
        return HairBookDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesRepository(newsDataSource: HairBookDataSource): ApiRepository {
        return ApiRepository(newsDataSource)
    }
}
package com.example.hairbookfront.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.data.remote.ApiService
import com.example.hairbookfront.data.remote.HairBookDataSource
import com.example.hairbookfront.data.remote.HairBookDataSourceImpl
import com.example.hairbookfront.domain.repository.ApiRepository
import com.example.hairbookfront.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(moshi: Moshi): Retrofit {
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

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile(DataStorePreferences.DATA) })
    }
}
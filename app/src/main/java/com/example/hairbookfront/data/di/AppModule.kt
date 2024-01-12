package com.example.hairbookfront.data.di

import com.example.hairbookfront.data.AppConstants
import com.example.hairbookfront.data.api.ApiService
import com.example.hairbookfront.data.datasource.NewsDataSource
import com.example.hairbookfront.data.datasource.NewsDataSourceImpl
import com.example.hairbookfront.ui.repository.HairBookRepository
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
class AppModule {

    //Create a single instance of Retrofit
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
            .baseUrl(AppConstants.BASE_URL)
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
    fun providesDataSource(apiService: ApiService): NewsDataSource {
        return NewsDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesRepository(newsDataSource: NewsDataSource): HairBookRepository {
        return HairBookRepository(newsDataSource)
    }

}
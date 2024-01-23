package com.example.hairbookfront.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceAuth
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceBarber
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceBooking
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceReview
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceAuth
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceBarber
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceBooking
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceReview
import com.example.hairbookfront.data.remote.DataSourcesImpls.HairBookDataSourceImplAuth
import com.example.hairbookfront.data.remote.DataSourcesImpls.HairBookDataSourceImplBarber
import com.example.hairbookfront.data.remote.DataSourcesImpls.HairBookDataSourceImplBooking
import com.example.hairbookfront.data.remote.DataSourcesImpls.HairBookDataSourceImplReview
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
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
    fun providesApiServiceAuth(retrofit: Retrofit): ApiServiceAuth {
        return retrofit.create(ApiServiceAuth::class.java)
    }

    @Provides
    @Singleton
    fun providesApiServiceBarber(retrofit: Retrofit): ApiServiceBarber {
        return retrofit.create(ApiServiceBarber::class.java)
    }

    @Provides
    @Singleton
    fun providesApiServiceBooking(retrofit: Retrofit): ApiServiceBooking {
        return retrofit.create(ApiServiceBooking::class.java)
    }

    @Provides
    @Singleton
    fun providesApiServiceReview(retrofit: Retrofit): ApiServiceReview {
        return retrofit.create(ApiServiceReview::class.java)
    }

    @Provides
    @Singleton
    fun providesDataSourceAuth(apiServiceAuth: ApiServiceAuth): HairBookDataSourceAuth {
        return HairBookDataSourceImplAuth(apiServiceAuth)
    }
    @Provides
    @Singleton
    fun providesDataSourceBarber(apiServiceBarber: ApiServiceBarber): HairBookDataSourceBarber {
        return HairBookDataSourceImplBarber(apiServiceBarber)
    }
    @Provides
    @Singleton
    fun providesDataSourceBooking(apiServiceBooking: ApiServiceBooking): HairBookDataSourceBooking {
        return HairBookDataSourceImplBooking(apiServiceBooking)
    }
    @Provides
    @Singleton
    fun providesDataSourceReview(apiServiceReview: ApiServiceReview): HairBookDataSourceReview {
        return HairBookDataSourceImplReview(apiServiceReview)
    }

    @Provides
    @Singleton
    fun providesRepositoryAuth(hairBookDataSourceAuth: HairBookDataSourceAuth): ApiRepositoryAuth {
        return ApiRepositoryAuth(hairBookDataSourceAuth)
    }

    @Provides
    @Singleton
    fun providesRepositoryBarber(hairBookDataSourceBarber: HairBookDataSourceBarber): ApiRepositoryBarber {
        return ApiRepositoryBarber(hairBookDataSourceBarber)
    }

    @Provides
    @Singleton
    fun providesRepositoryBooking(hairBookDataSourceBooking: HairBookDataSourceBooking): ApiRepositoryBooking {
        return ApiRepositoryBooking(hairBookDataSourceBooking)
    }

    @Provides
    @Singleton
    fun providesRepositoryReview(hairBookDataSourceReview: HairBookDataSourceReview): ApiRepositoryReview {
        return ApiRepositoryReview(hairBookDataSourceReview)
    }


    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile(DataStorePreferences.DATA) })
    }
}
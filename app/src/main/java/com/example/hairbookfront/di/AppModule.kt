package com.example.hairbookfront.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.data.remote.apiServices.ApiServiceAuth
import com.example.hairbookfront.data.remote.apiServices.ApiServiceBarber
import com.example.hairbookfront.data.remote.apiServices.ApiServiceBooking
import com.example.hairbookfront.data.remote.apiServices.ApiServiceCustomer
import com.example.hairbookfront.data.remote.apiServices.ApiServiceReview
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceAuth
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceBarber
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceBooking
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceCustomer
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceReview
import com.example.hairbookfront.data.remote.dataSourcesImpls.HairBookDataSourceImplAuth
import com.example.hairbookfront.data.remote.dataSourcesImpls.HairBookDataSourceImplBarber
import com.example.hairbookfront.data.remote.dataSourcesImpls.HairBookDataSourceImplBooking
import com.example.hairbookfront.data.remote.dataSourcesImpls.HairBookDataSourceImplCustomer
import com.example.hairbookfront.data.remote.dataSourcesImpls.HairBookDataSourceImplReview
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
import com.example.hairbookfront.domain.repository.ApiRepositoryCustomer
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

/**
 * Module for providing dependencies for the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    /**
     * Provides a singleton instance of Moshi.
     * @return A Moshi instance.
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Provides a singleton instance of Retrofit.
     *
     * @param moshi A Moshi instance.
     * @return A Retrofit instance.
     */
    @Provides
    @Singleton
    fun providesRetrofit(moshi: Moshi): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
        }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Provides a singleton instance of ApiServiceAuth.
     *
     * @param retrofit A Retrofit instance.
     * @return An ApiServiceAuth instance.
     */
    @Provides
    @Singleton
    fun providesApiServiceAuth(retrofit: Retrofit): ApiServiceAuth {
        return retrofit.create(ApiServiceAuth::class.java)
    }

    /**
     * Provides a singleton instance of ApiServiceBarber.
     *
     * @param retrofit A Retrofit instance.
     * @return An ApiServiceBarber instance.
     */
    @Provides
    @Singleton
    fun providesApiServiceBarber(retrofit: Retrofit): ApiServiceBarber {
        return retrofit.create(ApiServiceBarber::class.java)
    }

    /**
     * Provides a singleton instance of ApiServiceBooking.
     *
     * @param retrofit A Retrofit instance.
     * @return An ApiServiceBooking instance.
     */
    @Provides
    @Singleton
    fun providesApiServiceBooking(retrofit: Retrofit): ApiServiceBooking {
        return retrofit.create(ApiServiceBooking::class.java)
    }

    /**
     * Provides a singleton instance of ApiServiceReview.
     *
     * @param retrofit A Retrofit instance.
     * @return An ApiServiceReview instance.
     */
    @Provides
    @Singleton
    fun providesApiServiceReview(retrofit: Retrofit): ApiServiceReview {
        return retrofit.create(ApiServiceReview::class.java)
    }

    /**
     * Provides a singleton instance of ApiServiceCustomer.
     *
     * @param retrofit A Retrofit instance.
     * @return An ApiServiceCustomer instance.
     */
    @Provides
    @Singleton
    fun providesApiServiceCustomer(retrofit: Retrofit): ApiServiceCustomer {
        return retrofit.create(ApiServiceCustomer::class.java)
    }

    /**
     * Provides a singleton instance of HairBookDataSourceAuth.
     *
     * @param apiServiceAuth An ApiServiceAuth instance.
     * @return A HairBookDataSourceAuth instance.
     */
    @Provides
    @Singleton
    fun providesDataSourceAuth(apiServiceAuth: ApiServiceAuth): HairBookDataSourceAuth {
        return HairBookDataSourceImplAuth(apiServiceAuth)
    }

    /**
     * Provides a singleton instance of HairBookDataSourceBarber.
     *
     * @param apiServiceBarber An ApiServiceBarber instance.
     * @return A HairBookDataSourceBarber instance.
     */
    @Provides
    @Singleton
    fun providesDataSourceBarber(apiServiceBarber: ApiServiceBarber): HairBookDataSourceBarber {
        return HairBookDataSourceImplBarber(apiServiceBarber)
    }

    /**
     * Provides a singleton instance of HairBookDataSourceBooking.
     *
     * @param apiServiceBooking An ApiServiceBooking instance.
     * @return A HairBookDataSourceBooking instance.
     */
    @Provides
    @Singleton
    fun providesDataSourceBooking(apiServiceBooking: ApiServiceBooking): HairBookDataSourceBooking {
        return HairBookDataSourceImplBooking(apiServiceBooking)
    }

    /**
     * Provides a singleton instance of HairBookDataSourceReview.
     *
     * @param apiServiceReview An ApiServiceReview instance.
     * @return A HairBookDataSourceReview instance.
     */
    @Provides
    @Singleton
    fun providesDataSourceReview(apiServiceReview: ApiServiceReview): HairBookDataSourceReview {
        return HairBookDataSourceImplReview(apiServiceReview)
    }

    /**
     * Provides a singleton instance of HairBookDataSourceCustomer.
     *
     * @param apiServiceCustomer An ApiServiceCustomer instance.
     * @return A HairBookDataSourceCustomer instance.
     */
    @Provides
    @Singleton
    fun providesDataSourceCustomer(apiServiceCustomer: ApiServiceCustomer): HairBookDataSourceCustomer {
        return HairBookDataSourceImplCustomer(apiServiceCustomer)
    }

    /**
     * Provides a singleton instance of ApiRepositoryAuth.
     *
     * @param hairBookDataSourceAuth A HairBookDataSourceAuth instance.
     * @return An ApiRepositoryAuth instance.
     */
    @Provides
    @Singleton
    fun providesRepositoryAuth(hairBookDataSourceAuth: HairBookDataSourceAuth): ApiRepositoryAuth {
        return ApiRepositoryAuth(hairBookDataSourceAuth)
    }

    /**
     * Provides a singleton instance of ApiRepositoryBarber.
     *
     * @param hairBookDataSourceBarber A HairBookDataSourceBarber instance.
     * @return An ApiRepositoryBarber instance.
     */
    @Provides
    @Singleton
    fun providesRepositoryBarber(hairBookDataSourceBarber: HairBookDataSourceBarber): ApiRepositoryBarber {
        return ApiRepositoryBarber(hairBookDataSourceBarber)
    }

    /**
     * Provides a singleton instance of ApiRepositoryBooking.
     *
     * @param hairBookDataSourceBooking A HairBookDataSourceBooking instance.
     * @return An ApiRepositoryBooking instance.
     */
    @Provides
    @Singleton
    fun providesRepositoryBooking(hairBookDataSourceBooking: HairBookDataSourceBooking): ApiRepositoryBooking {
        return ApiRepositoryBooking(hairBookDataSourceBooking)
    }

    /**
     * Provides a singleton instance of ApiRepositoryReview.
     *
     * @param hairBookDataSourceReview A HairBookDataSourceReview instance.
     * @return An ApiRepositoryReview instance.
     */
    @Provides
    @Singleton
    fun providesRepositoryReview(hairBookDataSourceReview: HairBookDataSourceReview): ApiRepositoryReview {
        return ApiRepositoryReview(hairBookDataSourceReview)
    }

    /**
     * Provides a singleton instance of ApiRepositoryCustomer.
     *
     * @param hairBookDataSourceCustomer A HairBookDataSourceCustomer instance.
     * @return An ApiRepositoryCustomer instance.
     */
    @Provides
    @Singleton
    fun providesRepositoryApi(hairBookDataSourceCustomer: HairBookDataSourceCustomer): ApiRepositoryCustomer {
        return ApiRepositoryCustomer(hairBookDataSourceCustomer)
    }


    /**
     * Provides a singleton instance of DataStore<Preferences>.
     *
     * @param context The application context.
     * @return A DataStore<Preferences> instance.
     */
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile(DataStorePreferences.DATA) })
    }
}
package com.minhnn.taipeitour.di

import android.app.Application
import android.content.Context
import com.minhnn.taipeitour.data.AppConstants
import com.minhnn.taipeitour.data.api.ApiService
import com.minhnn.taipeitour.data.datasource.AttractionsDataSource
import com.minhnn.taipeitour.data.datasource.AttractionsDataSourceImpl
import com.minhnn.taipeitour.ui.repository.AttractionsRepository
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
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providesSharedPreferencesLanguageProvider(context: Context): SharedPreferencesLanguageProvider {
        return SharedPreferencesLanguageProvider(context)
    }

    @Provides
    @Singleton
    fun providesRetrofit(languageProvider: SharedPreferencesLanguageProvider): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {

            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl(AppConstants.APP_BASE_URL + languageProvider.getLanguage() + "/")
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
    fun providesAttractionsDataSource(apiService: ApiService): AttractionsDataSource {
        return AttractionsDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesAttractionsRepository(attractionsDataSource: AttractionsDataSource): AttractionsRepository {
        return AttractionsRepository(attractionsDataSource)
    }
}



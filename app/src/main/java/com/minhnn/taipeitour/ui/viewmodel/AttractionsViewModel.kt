package com.minhnn.taipeitour.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minhnn.taipeitour.data.AppConstants
import com.minhnn.taipeitour.data.api.ApiService
import com.minhnn.taipeitour.data.datasource.AttractionsDataSourceImpl
import com.minhnn.taipeitour.data.entity.AttractionsResponse
import com.minhnn.taipeitour.di.SharedPreferencesLanguageProvider
import com.minhnn.taipeitour.ui.repository.AttractionsRepository
import com.minhnn.utitlities1.ResourcesState
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AttractionsViewModel @Inject constructor(
    private var attractionsRepository: AttractionsRepository,
    private val languageProvider: SharedPreferencesLanguageProvider
) : ViewModel() {

    private val _attractions: MutableStateFlow<ResourcesState<AttractionsResponse>> =
        MutableStateFlow(ResourcesState.Loading())
    val attractions: StateFlow<ResourcesState<AttractionsResponse>> = _attractions

    private val _language: MutableStateFlow<String> =
        MutableStateFlow(languageProvider.getLanguage())
    val language: StateFlow<String> = _language

    init {
        getAttractions(AppConstants.PAGE)
    }

    private fun getAttractions(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            attractionsRepository.getAttractions(page)
                .collectLatest { attractionsResponse ->
                    _attractions.value = attractionsResponse
                }
        }
    }

    fun updateLanguage(language: String) {
        languageProvider.setLanguage(language)
        _language.value = language
        reinitializeRetrofit(language)
        getAttractions(AppConstants.PAGE)
    }

    private fun reinitializeRetrofit(language: String) {
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

        val newRetrofit = Retrofit.Builder()
            .baseUrl(AppConstants.APP_BASE_URL + language + "/")
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val newApiService = newRetrofit.create(ApiService::class.java)
        val newDataSource = AttractionsDataSourceImpl(newApiService)
        val newRepository = AttractionsRepository(newDataSource)

        // Update repository to use the new data source
        attractionsRepository = newRepository
    }

    companion object {
        const val TAG = "AttractionsViewModel"
    }
}

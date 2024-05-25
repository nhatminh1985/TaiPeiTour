package com.minhnn.taipeitour.data.datasource

import com.minhnn.taipeitour.data.api.ApiService
import com.minhnn.taipeitour.data.entity.AttractionsResponse
import retrofit2.Response
import javax.inject.Inject

class AttractionsDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : AttractionsDataSource {

    override suspend fun getAttractions(page: Int): Response<AttractionsResponse> {
        return apiService.getAttractions(page)
    }
}
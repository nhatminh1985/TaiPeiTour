package com.minhnn.taipeitour.data.datasource

import com.minhnn.taipeitour.data.entity.AttractionsResponse
import retrofit2.Response

interface AttractionsDataSource {

    suspend fun getAttractions(page: Int): Response<AttractionsResponse>
}
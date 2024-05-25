package com.minhnn.taipeitour.data.api

import com.minhnn.taipeitour.data.entity.AttractionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("accept: application/json")
    @GET("Attractions/All")
    suspend fun getAttractions(
        @Query("page") page: Int
    ): Response<AttractionsResponse>
}
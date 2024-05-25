package com.minhnn.taipeitour.ui.repository

import com.minhnn.taipeitour.data.datasource.AttractionsDataSource
import com.minhnn.taipeitour.data.entity.AttractionsResponse
import com.minhnn.utitlities1.ResourcesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AttractionsRepository @Inject constructor(
    private val attractionsDataSource: AttractionsDataSource
) {

    suspend fun getAttractions(page: Int): Flow<ResourcesState<AttractionsResponse>> {
        return flow {
            emit(ResourcesState.Loading())

            val response = attractionsDataSource.getAttractions(page)

            if (response.isSuccessful && response.body() != null) {
                emit(ResourcesState.Success(response.body()!!))
            } else {
                emit(ResourcesState.Error("Error fetching news data"))
            }
        }.catch { e ->
            emit(ResourcesState.Error(e.localizedMessage ?: "Some error is flow"))
        }
    }
}
package com.nifa.fuel_buddy.core.data.repository

import com.nifa.fuel_buddy.core.data.networkSource.CommonNetworkSource
import com.nifa.fuel_buddy.core.domain.CommonRepository
import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val networkSource: CommonNetworkSource
) : CommonRepository {
    override suspend fun submitFeedback(request: SubmitFeedbackRequest): Flow<Result<Unit, NetworkError>> {
        return networkSource.submitFeedback(request).map { result ->
            when (result) {
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading(result.isLoading)
                is Result.Success -> Result.Success(Unit)
            }
        }
    }
}
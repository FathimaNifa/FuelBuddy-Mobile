package com.nifa.fuel_buddy.core.data.networkSource

import com.nifa.fuel_buddy.core.domain.NetworkError
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.domain.SuccessResponse
import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import kotlinx.coroutines.flow.Flow

interface CommonNetworkSource {

    suspend fun submitFeedback(request: SubmitFeedbackRequest): Flow<Result<SuccessResponse, NetworkError>>
}
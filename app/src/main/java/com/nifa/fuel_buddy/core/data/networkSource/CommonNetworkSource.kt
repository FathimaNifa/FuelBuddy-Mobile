package com.nifa.fuel_buddy.core.data.networkSource

import com.nifa.fuel_buddy.core.domain.model.SuccessResponse
import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import com.nifa.fuel_buddy.core.domain.util.NetworkError
import com.nifa.fuel_buddy.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CommonNetworkSource {

    suspend fun submitFeedback(request: SubmitFeedbackRequest): Flow<Result<SuccessResponse, NetworkError>>
}
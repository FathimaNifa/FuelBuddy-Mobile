package com.nifa.fuel_buddy.core.domain

import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import com.nifa.fuel_buddy.core.domain.util.NetworkError
import com.nifa.fuel_buddy.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    suspend fun submitFeedback(request: SubmitFeedbackRequest): Flow<Result<Unit, NetworkError>>
}
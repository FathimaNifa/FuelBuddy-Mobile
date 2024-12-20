package com.nifa.fuel_buddy.core.domain

import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import kotlinx.coroutines.flow.Flow

interface CommonRepository {

    suspend fun submitFeedback(request: SubmitFeedbackRequest): Flow<Result<Unit, NetworkError>>
}
package com.nifa.fuel_buddy.core.data.networkSource

import com.nifa.fuel_buddy.core.domain.model.SuccessResponse
import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import com.nifa.fuel_buddy.core.domain.util.NetworkError
import com.nifa.fuel_buddy.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeCommonNetworkSource @Inject constructor(): CommonNetworkSource {
    override suspend fun submitFeedback(request: SubmitFeedbackRequest): Flow<Result<SuccessResponse, NetworkError>> {
        return flow {
            emit(
                Result.Success(
                    SuccessResponse(
                        statusCode = 200,
                        message = "Success"
                    )
                )
            )
        }
    }
}
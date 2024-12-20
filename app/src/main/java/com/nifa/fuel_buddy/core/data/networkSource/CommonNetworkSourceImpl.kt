package com.nifa.fuel_buddy.core.data.networkSource

import android.content.Context
import com.nifa.fuel_buddy.core.domain.model.SuccessResponse
import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import com.nifa.fuel_buddy.core.domain.util.BaseApiResponse
import com.nifa.fuel_buddy.core.domain.util.NetworkError
import com.nifa.fuel_buddy.core.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonNetworkSourceImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val api: CommonApi
) : CommonNetworkSource, BaseApiResponse(context) {
    override suspend fun submitFeedback(request: SubmitFeedbackRequest): Flow<Result<SuccessResponse, NetworkError>> {
        return safeApiCall { api.submitFeedback(request) }
    }
}
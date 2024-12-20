package com.nifa.fuel_buddy.core.data.networkSource

import com.nifa.fuel_buddy.core.domain.model.SuccessResponse
import com.nifa.fuel_buddy.core.domain.model.request.SubmitFeedbackRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CommonApi {

    @POST("user/submitFeedback")
    suspend fun submitFeedback(@Body request : SubmitFeedbackRequest) : Response<SuccessResponse>
}
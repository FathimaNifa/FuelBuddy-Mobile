package com.nifa.fuel_buddy.core.domain

import android.content.Context
import com.nifa.fuel_buddy.core.utils.ext.isConnectedToNetwork
import retrofit2.Response

open class BaseApiResponse(private val context: Context) {

    suspend fun <T> safeApiCall(api: suspend () -> Response<T>): Result<T, NetworkError> {
        return when {
            context.isConnectedToNetwork() -> {
                try {
                    val response = api.invoke()
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            Result.Success(data)
                        } ?: Result.Error(NetworkError.BAD_RESPONSE)
                    } else {
                        Result.Error(NetworkError.BAD_RESPONSE)
                    }
                } catch (e: Exception) {
                    Result.Error(NetworkError.UNKNOWN)
                }
            }
            else -> {
                Result.Error(NetworkError.NO_INTERNET)
            }
        }
    }
}

enum class NetworkError : RootError {
    NO_INTERNET,
    BAD_RESPONSE,
    UNKNOWN
}
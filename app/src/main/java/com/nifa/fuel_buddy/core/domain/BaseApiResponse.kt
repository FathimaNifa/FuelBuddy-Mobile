package com.nifa.fuel_buddy.core.domain

import android.content.Context
import com.nifa.fuel_buddy.core.utils.ext.isConnectedToNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

open class BaseApiResponse(private val context: Context) {

    fun <T> safeApiCall(api: suspend () -> Response<T>): Flow<Result<T, NetworkError>> {
        return flow {
            emit(Result.Loading(true))
            when {
                context.isConnectedToNetwork() -> {
                    try {
                        val response = api.invoke()
                        if (response.isSuccessful) {
                            response.body()?.let { data ->
                                emit(Result.Success(data))
                            } ?: emit(Result.Error(NetworkError.BAD_RESPONSE))
                        } else {
                            emit(Result.Error(NetworkError.BAD_RESPONSE))
                        }

                    } catch (e: Exception) {
                        emit(Result.Error(NetworkError.UNKNOWN))
                    }

                }

                else -> {
                    emit(Result.Error(NetworkError.NO_INTERNET))
                }
            }
            emit(Result.Loading(false))
        }
    }
}

enum class NetworkError : RootError {
    NO_INTERNET,
    BAD_RESPONSE,
    UNKNOWN
}
package com.nifa.fuel_buddy.core.domain

import android.content.Context
import com.google.gson.Gson
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
                            } ?: emit(Result.Error(NetworkError("Success But No data")))
                        } else {
                            response.errorBody()?.let { errorBody ->
                                try {
                                    val errorResponse = Gson().fromJson(
                                        errorBody.string(),
                                        ErrorResponse::class.java
                                    )
                                    emit(Result.Error(NetworkError(errorResponse.message)))
                                } catch (e: Exception) {
                                    emit(Result.Error(NetworkError(e.message.toString())))
                                }
                            } ?: emit(Result.Error(NetworkError("Error But No Error Body")))
                        }

                    } catch (e: Exception) {
                        emit(Result.Error(NetworkError(e.message.toString())))
                    }

                }

                else -> {
                    emit(Result.Error(NetworkError("No Internet")))
                }
            }
            emit(Result.Loading(false))
        }
    }
}

data class NetworkError(val message: String) : Error

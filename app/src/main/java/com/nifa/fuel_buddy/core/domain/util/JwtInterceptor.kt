package com.nifa.fuel_buddy.core.domain.util

import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSource

import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(
    private val preferenceDataSource: PreferenceDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(NetworkConstant.AUTHORIZATION, preferenceDataSource.getJwtToken())
                .build()
        )
    }
}
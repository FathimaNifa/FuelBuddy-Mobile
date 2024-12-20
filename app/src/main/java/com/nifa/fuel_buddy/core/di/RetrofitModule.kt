package com.nifa.fuel_buddy.core.di

import com.nifa.fuel_buddy.BuildConfig
import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSource
import com.nifa.fuel_buddy.core.data.networkSource.CommonApi
import com.nifa.fuel_buddy.core.domain.util.JwtInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideOkHttp(
        preferenceDataSource: PreferenceDataSource
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(JwtInterceptor(preferenceDataSource))
            .build()

    @Provides
    @Singleton
    fun provideCommonApi(retrofit: Retrofit): CommonApi = retrofit.create()
}
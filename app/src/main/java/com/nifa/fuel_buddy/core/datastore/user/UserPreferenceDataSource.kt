package com.nifa.fuel_buddy.core.datastore.user

import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {

    val userPreferencesData: Flow<UserPreferences>

    suspend fun setUserEmail(emailId: String)

    suspend fun setUserId(id: String)

    suspend fun setUserName(name: String)

    suspend fun setUserToken(token: String)

    suspend fun clearAll()
}
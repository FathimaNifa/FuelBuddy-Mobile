package com.nifa.fuel_buddy.core.data.datastore.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nifa.fuel_buddy.core.utils.ext.nullAsEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferenceDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferenceDataSource {
    override val userPreferencesData: Flow<UserPreferences>
        get() = dataStore.data
            .catch { t ->
                // datastore data throws an IOException when an error is encountered when reading data
                if (t is IOException)
                    emit(emptyPreferences())
                else
                    throw t
            }.map { preferences ->
                UserPreferences(
                    userId = preferences[UserId].nullAsEmpty(),
                    userName = preferences[UserName].nullAsEmpty(),
                    userEmail = preferences[UserEmail].nullAsEmpty(),
                    userToken = preferences[UserToken].nullAsEmpty()
                )
            }

    override suspend fun setUserEmail(emailId: String) {
        dataStore.edit { preferences ->
            preferences[UserEmail] = emailId
        }
    }

    override suspend fun setUserId(id: String) {
        dataStore.edit { preferences ->
            preferences[UserId] = id
        }
    }

    override suspend fun setUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[UserName] = name
        }
    }

    override suspend fun setUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[UserToken] = token
        }
    }

    override suspend fun setUserPreferences(userPreferences: UserPreferences) {
        setUserId(userPreferences.userId)
        setUserEmail(userPreferences.userEmail)
        setUserName(userPreferences.userName)
        setUserToken(userPreferences.userToken)
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    companion object {
        val UserId = stringPreferencesKey("user_id")
        val UserName = stringPreferencesKey("user_name")
        val UserEmail = stringPreferencesKey("user_email")
        val UserToken = stringPreferencesKey("user_token")
    }
}
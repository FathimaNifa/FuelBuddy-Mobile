package com.nifa.fuel_buddy.core.datastore.fuelstation

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

class FuelStationPreferenceDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : FuelStationPreferenceDataSource {
    override val fuelStationPreferencesData: Flow<FuelStationPreferences>
        get() = dataStore.data
            .catch { t ->
                // datastore data throws an IOException when an error is encountered when reading data
                if (t is IOException)
                    emit(emptyPreferences())
                else
                    throw t
            }.map { preferences ->
                FuelStationPreferences(
                    bunkId = preferences[BunkId].nullAsEmpty(),
                    bunkName = preferences[BunkName].nullAsEmpty(),
                    bunkEmail = preferences[BunkEmail].nullAsEmpty(),
                    bunkToken = preferences[BunkToken].nullAsEmpty()
                )
            }

    override suspend fun setFuelStationEmail(emailId: String) {
        dataStore.edit { preferences ->
            preferences[BunkEmail] = emailId
        }
    }

    override suspend fun setFuelStationId(id: String) {
        dataStore.edit { preferences ->
            preferences[BunkId] = id
        }
    }

    override suspend fun setFuelStationName(name: String) {
        dataStore.edit { preferences ->
            preferences[BunkName] = name
        }
    }

    override suspend fun setFuelStationToken(token: String) {
        dataStore.edit { preferences ->
            preferences[BunkToken] = token
        }
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    companion object {
        val BunkId = stringPreferencesKey("bunk_id")
        val BunkName = stringPreferencesKey("bunk_name")
        val BunkEmail = stringPreferencesKey("bunk_email")
        val BunkToken = stringPreferencesKey("bunk_token")
    }
}
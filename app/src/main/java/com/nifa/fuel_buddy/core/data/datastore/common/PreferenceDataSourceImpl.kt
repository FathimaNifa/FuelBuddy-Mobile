package com.nifa.fuel_buddy.core.data.datastore.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import com.nifa.fuel_buddy.core.utils.ext.nullAsEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataSource {
    override val preferenceData: Flow<AccountType?>
        get() = dataStore.data
            .catch { t ->
                // datastore data throws an IOException when an error is encountered when reading data
                if (t is IOException)
                    emit(emptyPreferences())
                else
                    throw t
            }.map { preferences ->
                preferences[AccountTypeKey].nullAsEmpty()
            }.map { accountType ->
                AccountType.entries.firstOrNull {
                    it.name == accountType
                }
            }

    override suspend fun setAccountType(accountType: AccountType) {
        dataStore.edit { preferences ->
            preferences[AccountTypeKey] = accountType.name
        }
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    companion object {
        val AccountTypeKey = stringPreferencesKey("account_type")
    }
}
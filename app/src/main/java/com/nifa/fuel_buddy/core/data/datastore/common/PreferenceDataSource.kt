package com.nifa.fuel_buddy.core.data.datastore.common

import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {

    val preferenceData: Flow<AccountType?>

    suspend fun setAccountType(accountType: AccountType)

    suspend fun clearAll()
}
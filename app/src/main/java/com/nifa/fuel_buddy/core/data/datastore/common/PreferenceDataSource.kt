package com.nifa.fuel_buddy.core.data.datastore.common

import com.nifa.fuel_buddy.auth.presentation.feature.accounttype.AccountType
import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {

    val accountTypeFlow: Flow<AccountType?>

    val jwtTokenFlow: Flow<String>

    suspend fun setAccountType(accountType: AccountType)

    suspend fun setJwtToken(token: String)

    suspend fun clearAll()
}
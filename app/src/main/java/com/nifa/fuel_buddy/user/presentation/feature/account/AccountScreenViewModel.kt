package com.nifa.fuel_buddy.user.presentation.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.core.datastore.user.UserPreferenceDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AccountScreenUiState()
    )

    private fun observeAndUpdateUserEmail() {
        userPreferenceDataSource.userPreferencesData
            .map { it.userEmail }
            .distinctUntilChanged()
            .onEach { updateUserEmailUiState(it) }
            .launchIn(viewModelScope)
    }

    private fun observeAndUpdateUserName() {
        userPreferenceDataSource.userPreferencesData
            .map { it.userName }
            .distinctUntilChanged()
            .onEach { updateUserNameUiState(it) }
            .launchIn(viewModelScope)
    }

    init {
        observeAndUpdateUserEmail()
        observeAndUpdateUserName()
    }

    fun onUiAction(action: AccountScreenUiAction) {
        when (action) {
            AccountScreenUiAction.OnChangePasswordClicked -> Unit
            AccountScreenUiAction.OnLogoutButtonClicked -> logoutUser()
            AccountScreenUiAction.OnSupportAndFeedBackClicked -> Unit
        }
    }

    private fun logoutUser() = viewModelScope.launch {
        userPreferenceDataSource.clearAll()
    }

    private fun updateUserEmailUiState(userEmail: String): Unit =
        _uiState.update {
            it.copy(
                userEmail = userEmail
            )
        }

    private fun updateUserNameUiState(userName: String): Unit =
        _uiState.update {
            it.copy(
                userName = userName
            )
        }
}

data class AccountScreenUiState(
    val userEmail: String = "",
    val userName: String = ""
)

sealed interface AccountScreenUiAction {
    data object OnChangePasswordClicked : AccountScreenUiAction
    data object OnSupportAndFeedBackClicked : AccountScreenUiAction
    data object OnLogoutButtonClicked : AccountScreenUiAction
}
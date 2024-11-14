package com.nifa.fuel_buddy.user.presentation.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nifa.fuel_buddy.user.presentation.navigation.bottomnavigation.BottomNavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class UserNavMainScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserNavMainScreenViewModelUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = UserNavMainScreenViewModelUiState()
    )

    fun onUiAction(action: UserNavMainScreenViewModelUiAction) {
        when (action) {
            is UserNavMainScreenViewModelUiAction.OnNavDestinationChanged -> setBottomBarVisibility(
                action.route
            )
        }
    }

    private fun setBottomBarVisibility(route: String) {
        val shouldShowBottomBar = BottomNavigationItem.entries.any {
            it.screen::class.qualifiedName == route
        }
        updateShouldShowBottomBarUiState(shouldShowBottomBar)
    }

    private fun updateShouldShowBottomBarUiState(shouldShowBottomBar: Boolean): Unit =
        _uiState.update {
            it.copy(
                shouldShowBottomBar = shouldShowBottomBar
            )
        }
}

data class UserNavMainScreenViewModelUiState(
    val shouldShowBottomBar: Boolean = true
)

sealed interface UserNavMainScreenViewModelUiAction {
    data class OnNavDestinationChanged(val route: String) : UserNavMainScreenViewModelUiAction
}
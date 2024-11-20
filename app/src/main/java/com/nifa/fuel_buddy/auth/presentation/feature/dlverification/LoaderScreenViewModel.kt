package com.nifa.fuel_buddy.auth.presentation.feature.dlverification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nifa.fuel_buddy.auth.domain.AuthRepository
import com.nifa.fuel_buddy.auth.domain.model.request.UserSignUpRequest
import com.nifa.fuel_buddy.auth.presentation.navigation.AuthNavigation
import com.nifa.fuel_buddy.core.domain.Result
import com.nifa.fuel_buddy.core.utils.CustomNavType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class LoaderScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoaderScreenUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = LoaderScreenUiState()
    )

    init {

        val data = savedStateHandle.toRoute<AuthNavigation.LoaderScreen>(
            typeMap = mapOf(
                typeOf<UserSignUpRequest>() to CustomNavType.UserSignUpRequestType,
            )
        )

        singUp(data.userSingUpRequest)
    }

    private fun singUp(signUpRequest: UserSignUpRequest) = viewModelScope.launch {
        authRepository.userSignUp(signUpRequest).collect { result ->
            when (result) {
                is Result.Error -> Unit
                is Result.Loading -> {
                    if (result.isLoading)
                        updateScreenStateUiState(LoaderScreenState.LOADING)
                }

                is Result.Success -> {
                    updateScreenStateUiState(LoaderScreenState.VERIFIED)
                    delay(1000L)
                    val data = result.data
                    authRepository.setUserPreferences(data)
                }
            }
        }
    }


    private fun updateScreenStateUiState(screenState: LoaderScreenState): Unit =
        _uiState.update {
            it.copy(
                screenState = screenState
            )
        }

}

data class LoaderScreenUiState(
    val screenState: LoaderScreenState = LoaderScreenState.LOADING
)

enum class LoaderScreenState {
    LOADING,
    VERIFIED
}
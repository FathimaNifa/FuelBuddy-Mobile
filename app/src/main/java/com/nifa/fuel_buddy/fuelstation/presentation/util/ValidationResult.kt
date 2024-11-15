package com.nifa.fuel_buddy.fuelstation.presentation.util

import com.nifa.fuel_buddy.core.utils.UiText

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: UiText? = null
)
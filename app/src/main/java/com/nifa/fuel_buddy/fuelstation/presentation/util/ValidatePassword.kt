package com.nifa.fuel_buddy.fuelstation.presentation.util

import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.UiText

object ValidatePassword {

    fun validate(password: String): ValidationResult {
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }

        return when {
            password.length < MIN_PASSWORD_LENGTH -> {
                ValidationResult(
                    isSuccessful = false,
                    errorMessage = UiText.StringResource(R.string.error_password_min_length)
                )
            }

            !containsLettersAndDigits -> {
                ValidationResult(
                    isSuccessful = false,
                    errorMessage = UiText.StringResource(R.string.error_password_mix_characters)
                )
            }

            else -> {
                ValidationResult(
                    isSuccessful = true
                )
            }
        }
    }

    private const val MIN_PASSWORD_LENGTH = 8
}
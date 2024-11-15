package com.nifa.fuel_buddy.auth.util

import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.UiText

object ValidateConfirmPassword {
    fun execute(password: String, confirmPassword: String): ValidationResult {
        return if(password != confirmPassword) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(R.string.error_password_not_match)
            )
        } else {
            ValidationResult(
                isSuccessful = true
            )
        }
    }
}
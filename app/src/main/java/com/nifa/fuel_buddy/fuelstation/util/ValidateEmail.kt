package com.nifa.fuel_buddy.fuelstation.util

import android.util.Patterns
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.UiText

object ValidateEmail {

    fun validate(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(R.string.email_not_blank)
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(R.string.not_valid_email)
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}
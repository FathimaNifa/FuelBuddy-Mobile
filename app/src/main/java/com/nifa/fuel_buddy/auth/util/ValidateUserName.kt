package com.nifa.fuel_buddy.auth.util

import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.UiText

object ValidateUserName {

    fun execute(text: String?): ValidationResult {
        if (text.isNullOrBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(R.string.user_name_not_blank)
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}
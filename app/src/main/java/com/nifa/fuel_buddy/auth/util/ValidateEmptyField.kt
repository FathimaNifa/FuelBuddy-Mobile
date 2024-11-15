package com.nifa.fuel_buddy.auth.util

import androidx.annotation.StringRes
import com.nifa.fuel_buddy.core.utils.UiText

object ValidateEmptyField {

    fun execute(text: String?, @StringRes stringResId: Int): ValidationResult {
        if (text.isNullOrBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(stringResId)
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}
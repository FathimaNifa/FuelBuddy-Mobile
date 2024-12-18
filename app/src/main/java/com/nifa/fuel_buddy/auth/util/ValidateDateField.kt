package com.nifa.fuel_buddy.auth.util

import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.UiText
import java.text.SimpleDateFormat
import java.util.Locale

object ValidateDateField {

    private const val DATE_NUM_REGEX = """^\d{2}-\d{2}-\d{4}${'$'}"""

    fun execute(text: String): ValidationResult {

        if (text.isBlank())
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(R.string.date_not_blank)
            )

        if(!text.matches(DATE_NUM_REGEX.toRegex()))
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(R.string.error_date_follows_pattern)
            )

        try {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(text)
        } catch (e: Exception) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(R.string.invalid_date)
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}
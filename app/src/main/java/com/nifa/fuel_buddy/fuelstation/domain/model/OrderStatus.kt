package com.nifa.fuel_buddy.fuelstation.domain.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.nifa.fuel_buddy.R

enum class OrderStatus(
    @StringRes val textStringResId: Int, @ColorRes val buttonColorResId: Int
) {
    DELIVERED(
        textStringResId = R.string.delivered,
        buttonColorResId = R.color.pigment_green
    ),
    CANCELLED(
        textStringResId = R.string.cancelled,
        buttonColorResId = R.color.dark_red
    ),
    New(
        textStringResId = R.string.new_order,
        buttonColorResId = R.color.sapphire
    ),
    PENDING(
        textStringResId = R.string.pending,
        buttonColorResId = R.color.orange
    )
}
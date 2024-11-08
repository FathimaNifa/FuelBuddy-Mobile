package com.nifa.fuel_buddy.presentation.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.utils.Font
import com.nifa.fuel_buddy.presentation.utils.ext.prependRupees

@Composable
fun OrderNowBottomCTA(
    modifier: Modifier = Modifier,
    totalPrice: Long,
    onClick: () -> Unit
) {
    BottomSheetCTA(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = stringResource(R.string.order_now),
            color = colorResource(R.color.black),
            fontSize = 14.sp,
            fontFamily = Font.JosefinSemiBold,
            modifier = Modifier.padding(20.dp)
        )

        Text(
            text = totalPrice.toString().prependRupees(),
            color = colorResource(R.color.black),
            fontSize = 14.sp,
            fontFamily = Font.JosefinSemiBold,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Preview
@Composable
private fun OrderNowBottomCTAPreview() {
    OrderNowBottomCTA(
        totalPrice = 500,
        onClick = {}
    )
}
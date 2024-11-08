package com.nifa.fuel_buddy.presentation.feature.activity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.utils.Font
import com.nifa.fuel_buddy.presentation.utils.ext.prependRupees

@Composable
fun PriceDetailsBottomBar(
    modifier: Modifier = Modifier,
    totalPrice: Long
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(colorResource(R.color.raisin_black))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                )
                .clip(RoundedCornerShape(8.dp)),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = stringResource(R.string.total),
                color = colorResource(R.color.saffron),
                fontSize = 14.sp,
                fontFamily = Font.JosefinSemiBold,
                modifier = Modifier.padding(20.dp)
            )

            Box(modifier = Modifier)

            Text(
                text = totalPrice.toString().prependRupees(),
                color = colorResource(R.color.saffron),
                fontSize = 14.sp,
                fontFamily = Font.JosefinSemiBold,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PriceDetailsBottomBarPreview() {
    PriceDetailsBottomBar(
        totalPrice = 590
    )
}
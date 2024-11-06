package com.nifa.fuel_buddy.presentation.feature.account.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.utils.Font

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    iconResId: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = colorResource(R.color.saffron).copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .background(colorResource(R.color.raisin_black))
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 20.dp,
                bottom = 20.dp
            )
    ) {
        Image(
            painter = painterResource(iconResId),
            contentDescription = null,
            modifier = Modifier
                .weight(1f),
            alignment = Alignment.CenterStart,
        )

        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = Font.JosefinSemiBold,
            color = colorResource(R.color.saffron),
            modifier = Modifier.weight(3f)
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun AccountScreenPreview() {
    ActionButton(
        text = "xyz@gmail.com",
        iconResId = R.drawable.ic_key,
        onClick = {}
    )
}

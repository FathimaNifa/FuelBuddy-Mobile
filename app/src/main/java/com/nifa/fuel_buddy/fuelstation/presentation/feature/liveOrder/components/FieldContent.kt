package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font

@Composable
fun FieldContent(
    modifier: Modifier = Modifier,
    @DrawableRes
    iconResId: Int,
    text: String
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconResId),
            tint = Color.Unspecified,
            contentDescription = null
        )

        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = Font.JosefinRegular,
            color = Color.White,
            lineHeight = 24.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Preview
@Composable
private fun FieldContentPreview() {
    FieldContent(
        text = "Sakthi Vinayakar Nagar, Injambakkam Chennai, Tamil Nadu 600115",
        iconResId = R.drawable.ic_account
    )
}
package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.prependHashTag
import com.nifa.fuel_buddy.core.utils.ext.prependRupees

@Composable
fun LiveOrderCard(
    modifier: Modifier = Modifier,
    orderNumber: String,
    totalPrice: String,
    location: String,
    awayFrom: String,
    onClick: () -> Unit,
    onAcceptButtonClicked: () -> Unit,
    onDeclineButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = colorResource(R.color.saffron).copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .background(colorResource(R.color.raisin_black))
            .padding(20.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row {

            Text(
                text = orderNumber.prependHashTag(),
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = Font.JosefinBold,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = totalPrice.prependRupees(),
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = Font.JosefinBold,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        Row {

            Text(
                text = location,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = Font.JosefinBold,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = awayFrom,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = Font.JosefinBold,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        Row {
            Box(
                modifier = Modifier.weight(1f),
            ) {
                CardCTA(
                    text = stringResource(R.string.accept),
                    backgroundColor = colorResource(R.color.pigment_green),
                    onClick = onAcceptButtonClicked,
                )
            }


            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.TopEnd
            ) {
                CardCTA(
                    text = stringResource(R.string.decline),
                    backgroundColor = colorResource(R.color.dark_red),
                    onClick = onDeclineButtonClicked,
                )
            }
        }
    }
}

@Preview
@Composable
private fun LiveOrderCardPreview() {
    LiveOrderCard(
        orderNumber = "33",
        totalPrice = "690",
        location = "Sakthi Vinayakar Nagar, Injambakkam\n" +
                "Chennai, Tamil Nadu 600115",
        awayFrom = "2.5 km away",
        onAcceptButtonClicked = {},
        onDeclineButtonClicked = {},
        onClick = {}
    )
}
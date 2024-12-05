package com.nifa.fuel_buddy.fuelstation.presentation.feature.history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderStatus
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components.CardCTA

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    orderNumber: String,
    totalPrice: String,
    orderStatus: OrderStatus,
    dateAndTime: String,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .border(
                width = 0.5.dp,
                color = colorResource(R.color.saffron).copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .background(colorResource(R.color.raisin_black))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            CardCTA(
                text = stringResource(orderStatus.textStringResId),
                backgroundColor = colorResource(orderStatus.buttonColorResId),
                onClick = {},
            )

            Text(
                text = dateAndTime,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = Font.JosefinRegular,
                textAlign = TextAlign.Right,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun HistoryCardPreview() {
    HistoryCard(
        orderNumber = "11",
        totalPrice = "200",
        orderStatus = OrderStatus.DELIVERED,
        dateAndTime = "20 Oct | 1.41 pm",
        onClick = {}
    )
}


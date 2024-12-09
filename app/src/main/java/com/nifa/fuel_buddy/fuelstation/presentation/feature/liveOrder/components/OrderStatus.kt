package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.fuelstation.domain.model.OrderStatus

@Composable
fun OrderStatus(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus
) {

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(orderStatus.buttonColorResId),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(ButtonDefaults.ContentPadding),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = stringResource(orderStatus.textStringResId),
            color = Color.White,
            fontFamily = Font.JosefinRegular,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }

}

@Preview
@Composable
private fun OrderStatusPreview() {
    OrderStatus(
        orderStatus = OrderStatus.CANCELLED
    )
}
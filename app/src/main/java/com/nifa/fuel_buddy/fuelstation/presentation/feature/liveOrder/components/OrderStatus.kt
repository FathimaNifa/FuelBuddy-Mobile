package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(
            width = 2.dp,
            color = colorResource(orderStatus.buttonColorResId)
        ),
        onClick = {}
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
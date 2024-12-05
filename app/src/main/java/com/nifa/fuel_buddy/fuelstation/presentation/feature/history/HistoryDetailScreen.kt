package com.nifa.fuel_buddy.fuelstation.presentation.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.prependHashTag
import com.nifa.fuel_buddy.core.utils.ext.prependRupees
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components.FieldContent
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components.OrderStatus

@Composable
fun HistoryDetailScreen(
    modifier: Modifier = Modifier,
    uiState: HistoryDetailScreenUiState
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = uiState.orderNumber.prependHashTag(),
            color = colorResource(R.color.white),
            fontSize = 22.sp,
            fontFamily = Font.JosefinBold,
            modifier = Modifier.padding(30.dp)
        )

        FieldContent(
            iconResId = R.drawable.ic_account,
            text = uiState.userName,
            modifier = Modifier.padding(start = 20.dp)
        )

        FieldContent(
            iconResId = R.drawable.ic_location,
            text = uiState.location,
            modifier = Modifier.padding(start = 20.dp)

        )

        FieldContent(
            iconResId = R.drawable.ic_rupees,
            text = uiState.totalPrice.prependRupees(),
            modifier = Modifier.padding(start = 20.dp)
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cart),
                tint = Color.Unspecified,
                contentDescription = null
            )

            OrderStatus(
                orderStatus = uiState.orderStatus
            )
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .border(
                    width = 0.5.dp,
                    color = colorResource(R.color.saffron).copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(colorResource(R.color.raisin_black)),
            contentPadding = PaddingValues(30.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {

            items(uiState.productList.size) { index ->

                val data = uiState.productList[index]

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        text = data.name,
                        fontSize = 12.sp,
                        fontFamily = Font.JosefinRegular,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier

                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = data.quantityAdded.toString(),
                            fontSize = 10.sp,
                            fontFamily = Font.JosefinRegular,
                            color = Color.White,
                            modifier = Modifier
                                .border(
                                    width = 0.5.dp,
                                    color = colorResource(R.color.saffron).copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 10.dp
                                )
                        )
                    }


                    Text(
                        text = (data.quantityAdded * data.price).toString().prependRupees(),
                        fontSize = 12.sp,
                        fontFamily = Font.JosefinRegular,
                        color = Color.White,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                ) {

                    Box(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(R.string.delivery_charge),
                        fontSize = 12.sp,
                        fontFamily = Font.JosefinRegular,
                        color = colorResource(R.color.saffron),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(3f)
                    )

                    Text(
                        text = uiState.deliveryCharge.prependRupees(),
                        fontSize = 12.sp,
                        fontFamily = Font.JosefinRegular,
                        color = Color.White,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun HistoryDetailScreenPreview() {
    HistoryDetailScreen(
        uiState = HistoryDetailScreenUiState()
    )
}
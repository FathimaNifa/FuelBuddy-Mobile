package com.nifa.fuel_buddy.presentation.feature.activity

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.data.quantityAddedFuelStationList
import com.nifa.fuel_buddy.presentation.feature.activity.components.PriceDetailsBottomBar
import com.nifa.fuel_buddy.presentation.utils.Font
import com.nifa.fuel_buddy.presentation.utils.ext.prependRupees

@Composable
fun ActivityDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ActivityDetailScreenUiState
) {

    uiState.fuelStation?.let { fuelStation ->


        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            containerColor = Color.Black,
            contentColor = colorResource(R.color.white),
            bottomBar = {
                PriceDetailsBottomBar(totalPrice = uiState.totalPrice)
            }
        ) { contentPadding ->

            val bottomPadding = contentPadding.calculateBottomPadding()

            Column(
                modifier = Modifier.padding(bottom = bottomPadding),

                ) {
                Text(
                    text = fuelStation.name,
                    color = colorResource(R.color.white),
                    fontSize = 22.sp,
                    fontFamily = Font.JosefinBold,
                    modifier = Modifier.padding(30.dp)
                )


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

                    items(fuelStation.productList.size) { index ->

                        val data = fuelStation.productList[index]

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
                                text = fuelStation.deliveryCharge.toString().prependRupees(),
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
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityDetailScreenPreview() {
    ActivityDetailScreen(
        uiState = ActivityDetailScreenUiState(
            fuelStation = quantityAddedFuelStationList[0]
        )
    )
}
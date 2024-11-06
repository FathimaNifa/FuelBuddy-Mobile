package com.nifa.fuel_buddy.presentation.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.data.dummyFuelStationList
import com.nifa.fuel_buddy.data.dummyProductList
import com.nifa.fuel_buddy.presentation.feature.home.components.ProductCard
import com.nifa.fuel_buddy.presentation.utils.Font
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    uiState: HomeDetailScreenUiState,
    uiAction: (HomeDetailUiAction) -> Unit,
    uiEvent: Flow<HomeDetailScreenUiEvent>
) {


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentPadding = PaddingValues(30.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        item {
            Text(
                text = uiState.title,
                color = colorResource(R.color.white),
                fontSize = 22.sp,
                fontFamily = Font.JosefinBold
            )
        }

        item {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(16f / 9f),
                painter = painterResource(id = R.drawable.hp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        items(uiState.productList.size) { index ->

            val data = uiState.productList[index]

            ProductCard(
                name = data.name,
                price = data.price,
                quantity = data.quantityAdded,
                imageDrawableId = R.drawable.petrol,
                addButtonClicked = {
                    uiAction.invoke(
                        HomeDetailUiAction.AddButtonClicked(
                            productId = data.productId
                        )
                    )
                },
                removeButtonClicked = {
                    uiAction.invoke(
                        HomeDetailUiAction.ReduceButtonClicked(
                            productId = data.productId
                        )
                    )
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDetailScreenPreview() {
    HomeDetailScreen(
        uiAction = { },
        uiEvent = emptyFlow(),
        uiState = HomeDetailScreenUiState(
            fuelStation = dummyFuelStationList[0],
            productList = dummyProductList
        )
    )
}
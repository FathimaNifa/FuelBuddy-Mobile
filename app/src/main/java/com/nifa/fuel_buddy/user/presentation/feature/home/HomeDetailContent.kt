package com.nifa.fuel_buddy.user.presentation.feature.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.imagePainter
import com.nifa.fuel_buddy.user.data.dummyFuelStationList
import com.nifa.fuel_buddy.user.data.dummyProductList
import com.nifa.fuel_buddy.user.presentation.feature.home.components.ProductCard
import com.nifa.fuel_buddy.user.presentation.feature.home.components.ViewCartBottomCTA

@Composable
fun HomeDetailContent(
    modifier: Modifier = Modifier,
    uiState: HomeDetailScreenUiState,
    uiAction: (HomeDetailUiAction) -> Unit,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black,
        contentColor = colorResource(R.color.white),
        bottomBar = {
            ViewCartBottomCTA(
                modifier = Modifier.windowInsetsPadding(NavigationBarDefaults.windowInsets),
                addedItemCount = uiState.addedItemCount,
                visibility = uiState.shouldShowCartCTABottomSheet,
                onClick = { uiAction.invoke(HomeDetailUiAction.ViewCartButtonClicked) }
            )
        }
    ) { contentPadding ->

        val bottomPadding by animateDpAsState(
            targetValue = if (uiState.shouldShowCartCTABottomSheet) contentPadding.calculateBottomPadding() else 0.dp,
            label = "bottom-padding"
        )
        LazyColumn(
            modifier = Modifier.padding(bottom = bottomPadding),
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
                    painter = imagePainter(uiState.imageUrl),
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
                    imageUrl = data.imageUrl,
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
}

@Preview
@Composable
private fun HomeDetailContentPreview() {
    HomeDetailContent(
        uiAction = { },
        uiState = HomeDetailScreenUiState(
            fuelStation = dummyFuelStationList[0],
            productList = dummyProductList,
            addedItemCount = 3,
            shouldShowCartCTABottomSheet = true
        )
    )
}
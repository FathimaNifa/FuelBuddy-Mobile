package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.CollectAsEffect
import com.nifa.fuel_buddy.core.utils.ext.openGoogleMaps
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.OutForDeliveryScreenViewModel.UiAction
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.OutForDeliveryScreenViewModel.UiEvent
import com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.OutForDeliveryScreenViewModel.UiState
import com.nifa.fuel_buddy.fuelstation.presentation.navigation.FuelStationNavigation
import com.nifa.fuel_buddy.user.presentation.feature.home.components.ActionButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun OutForDeliveryScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    uiAction: (UiAction) -> Unit,
    uiEvent: Flow<UiEvent>,
    navigateToCallback: (FuelStationNavigation) -> Unit
) {

    val context = LocalContext.current

    uiEvent.CollectAsEffect { event ->
        when (event) {
            is UiEvent.NavigateTo -> {
                navigateToCallback.invoke(event.screen)
            }

            is UiEvent.OpenGoogleMapApp -> {
                context.openGoogleMaps(
                    latitude = event.latitude,
                    longitude = event.longitude
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.black)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.out_for_deliver_title),
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = Font.JosefinBold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                contentDescription = "Profile Image",
                modifier = modifier
                    .padding(30.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.black))
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.take_me_to_map),
                    backgroundColor = colorResource(R.color.saffron),
                    textColor = colorResource(R.color.black),
                    onClick = { uiAction.invoke(UiAction.OnTakeMeToMapButtonClicked) }
                )
                ActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.delivered_order),
                    backgroundColor = colorResource(R.color.raisin_black),
                    textColor = colorResource(R.color.saffron),
                    onClick = { uiAction.invoke(UiAction.OnDeliveredOrderButtonClicked) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun OutForDeliveryScreenPreview() {
    OutForDeliveryScreen(
        uiState = UiState(),
        uiAction = {},
        uiEvent = emptyFlow(),
        navigateToCallback = {}
    )
}
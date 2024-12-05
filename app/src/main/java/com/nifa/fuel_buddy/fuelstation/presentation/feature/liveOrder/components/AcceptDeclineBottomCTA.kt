package com.nifa.fuel_buddy.fuelstation.presentation.feature.liveOrder.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nifa.fuel_buddy.R

@Composable
fun AcceptDeclineBottomCTA(
    modifier: Modifier = Modifier,
    onAcceptButtonClicked: () -> Unit,
    onDeclineButtonClicked: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(colorResource(R.color.raisin_black))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                    horizontal = 10.dp,
                )
                .clip(RoundedCornerShape(8.dp)),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CardCTA(
                text = stringResource(R.string.accept),
                backgroundColor = colorResource(R.color.pigment_green),
                onClick = onAcceptButtonClicked
            )

            CardCTA(
                text = stringResource(R.string.decline),
                backgroundColor = colorResource(R.color.dark_red),
                onClick = onDeclineButtonClicked
            )
        }
    }
}

@Preview
@Composable
private fun AcceptDeclineBottomCTAPreview() {
    AcceptDeclineBottomCTA(
        onAcceptButtonClicked = {},
        onDeclineButtonClicked = {}
    )
}
package com.nifa.fuel_buddy.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
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
import com.nifa.fuel_buddy.presentation.utils.Font

@Composable
fun LoaderScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.black))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.your_order_is_sent_to_petrol_station_waiting_for_the_confirmation),
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = Font.JosefinSemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(
                color = colorResource(R.color.saffron),
                trackColor = colorResource(R.color.white),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
            )
        }

        Box(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoaderScreenContentPreview() {
    LoaderScreenContent(modifier = Modifier)
}

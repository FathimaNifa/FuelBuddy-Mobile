package com.nifa.fuel_buddy.presentation.feature.home

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.feature.home.components.ActionButton
import com.nifa.fuel_buddy.presentation.utils.Font

@Composable
fun OrderApprovedScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.your_order_is_accepted),
                color = Color.White,
                fontSize = 16.sp,
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
                painter = painterResource(R.drawable.approved),
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
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton(
                    text = stringResource(R.string.track_order),
                    backgroundColor = colorResource(R.color.saffron),
                    textColor = colorResource(R.color.black),
                    onClick = {}
                )
                ActionButton(
                    text = stringResource(R.string.back_to_home),
                    backgroundColor = colorResource(R.color.raisin_black),
                    textColor = colorResource(R.color.saffron),
                    onClick = {}
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun OrderApprovedScreenContentPreview() {
    OrderApprovedScreenContent(
        modifier = Modifier,
    )
}
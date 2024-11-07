package com.nifa.fuel_buddy.presentation.feature.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R

@Composable
fun LoaderScreen(modifier: Modifier = Modifier, title: String, progress1: Float, progress2: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .background(colorResource(R.color.black)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        )

        Spacer(modifier = Modifier.height(150.dp))
        DividerProgressIndicator(
            progress1 = progress1,
            progress2 = progress2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .height(3.dp)
                .align(Alignment.CenterHorizontally)

        )
    }
}

@Composable
fun DividerProgressIndicator(
    progress1: Float, progress2: Float, modifier: Modifier = Modifier
) {
    val saffronColor = colorResource(R.color.saffron)

    Canvas(modifier = modifier) {
        val totalWidth = size.width
        val height = size.height
        val cornerRadius = height / 2
        val width1 = totalWidth * progress1
        val width2 = totalWidth * progress2
        drawRoundRect(
            color = saffronColor,
            size = Size(width1, height),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )
        drawRect(
            color = Color.Black,
            topLeft = Offset(x = width1, y = 0f),
            size = Size(4.dp.toPx(), height)
        )
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(x = width1 + 4.dp.toPx(), y = 0f),
            size = Size(width2, height),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoaderScreenPreview() {
    LoaderScreen(
        modifier = Modifier,
        title = "Your Order is sent to Petrol station, Waiting for the Confirmation",
        progress1 = 0.5f,
        progress2 = 0.5f
    )
}

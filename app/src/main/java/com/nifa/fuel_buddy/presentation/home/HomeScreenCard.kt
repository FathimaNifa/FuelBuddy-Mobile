package com.nifa.fuel_buddy.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R

@Composable
fun HomeScreenCard(
    modifier: Modifier = Modifier,
    imageDrawableId: Int,
    title: String,
    distance: String
) {
    Card(
        modifier = modifier.wrapContentSize(),
        border = BorderStroke(0.8.dp, colorResource(R.color.saffron)),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.raisin_black)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                painter = painterResource(id = imageDrawableId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Text(
                text = distance,
                color = Color.White,
                fontSize = 13.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun HomeScreenComponentPreview() {
    HomeScreenCard(
        imageDrawableId = R.drawable.bharat,
        title = "Bharat Petroleum",
        distance = "1.2 km away"
    )
}
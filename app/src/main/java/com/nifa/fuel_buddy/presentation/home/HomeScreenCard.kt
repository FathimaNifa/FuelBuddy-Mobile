package com.nifa.fuel_buddy.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        modifier = Modifier
            .width(350.dp)
            .height(280.dp),
        border = BorderStroke(0.8.dp, colorResource(R.color.saffron)),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.raisin_black)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageDrawableId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = distance,
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Left,

                )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenComponentPreview() {
    HomeScreenCard(
        imageDrawableId = R.drawable.bharat,
        title = "Bharat Petroleum",
        distance = "1.2 km away"
    )
}
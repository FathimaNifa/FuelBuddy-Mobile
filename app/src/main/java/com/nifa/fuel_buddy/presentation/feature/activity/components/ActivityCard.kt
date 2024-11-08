package com.nifa.fuel_buddy.presentation.feature.activity.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.Alignment
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
import com.nifa.fuel_buddy.presentation.utils.Font
import com.nifa.fuel_buddy.presentation.utils.ext.prependRupees

@Composable
fun ActivityCard(
    modifier: Modifier = Modifier,
    imageDrawableId: Int,
    title: String,
    price: String,
    datetime: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(130.dp)
            .clickable(onClick = onClick),
        border = BorderStroke(0.8.dp, colorResource(R.color.saffron).copy(0.5f)),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.raisin_black)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = imageDrawableId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(16/9f)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontFamily = Font.JosefinRegular,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                )
                Text(
                    text = price.prependRupees(),
                    fontSize = 12.sp,
                    fontFamily = Font.JosefinRegular,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                )
                Text(
                    text = datetime,
                    fontSize = 12.sp,
                    fontFamily = Font.JosefinRegular,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ActivityCardPreview() {
    ActivityCard(
        imageDrawableId = R.drawable.bharat,
        title = "Bharat Petroleum",
        price = "120",
        datetime = "20 Oct | 2:00PM",
        onClick = {}
    )
}
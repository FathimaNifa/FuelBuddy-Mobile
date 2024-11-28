package com.nifa.fuel_buddy.user.presentation.feature.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.core.utils.ext.appendAwayFrom
import com.nifa.fuel_buddy.core.utils.imagePainter

@Composable
fun HomeScreenCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    distance: String,
    ratings: Int,
    ratingCount: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .clickable(onClick = onClick),
        border = BorderStroke(0.8.dp, colorResource(R.color.saffron).copy(0.5f)),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.raisin_black)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                painter = imagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = Font.JosefinBold,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = colorResource(R.color.saffron)
                    )
                    Text(
                        text = "$ratings ($ratingCount)",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = Font.JosefinBold,
                        textAlign = TextAlign.Left,
                    )
                }

                Text(
                    text = distance.appendAwayFrom(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = Font.JosefinRegular,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun HomeScreenComponentPreview() {
    HomeScreenCard(
        title = "Bharat Petroleum",
        distance = "1.2",
        onClick = {},
        imageUrl = "",
        ratingCount = 20,
        ratings = 4
    )
}
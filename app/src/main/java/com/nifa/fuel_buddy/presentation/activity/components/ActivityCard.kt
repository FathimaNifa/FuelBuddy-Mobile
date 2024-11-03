package com.nifa.fuel_buddy.presentation.activity.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R

@Composable
fun ActivityCard(
    modifier: Modifier = Modifier,
    imageDrawableId: Int,
    title: String,
    price: String,
    datetime: String
) {
    Card(
        modifier = modifier.wrapContentSize(),
        border = BorderStroke(0.8.dp, colorResource(R.color.saffron)),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.raisin_black)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(

                painter = painterResource(id = imageDrawableId),
                contentDescription = null,
                modifier = Modifier
                    .padding(25.dp)
                    .clip(RoundedCornerShape(15.dp))
            )

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = price,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = datetime,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
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
        price = "Rs.120",
        datetime = "20 Oct | 2:00PM"
    )
}
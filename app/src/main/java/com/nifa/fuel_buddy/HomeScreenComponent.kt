package com.nifa.fuel_buddy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val CustomYellow = Color(0xFFF2C94C)
val CustomBlack = Color(0xFF1D1B20)

@Composable
fun HomeScreenComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // First Card
            Card(
                modifier = Modifier
                    .width(350.dp)
                    .height(280.dp),
                border = BorderStroke(0.8.dp, CustomYellow),
                colors = CardDefaults.cardColors(containerColor = CustomBlack),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.bharat),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Bharath Petroleum",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "1.2 km away",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Left
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Second Card
            Card(
                modifier = Modifier
                    .width(350.dp)
                    .height(280.dp),
                border = BorderStroke(0.8.dp, CustomYellow),
                colors = CardDefaults.cardColors(containerColor = CustomBlack),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.hp),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "HP Petrol Bunk",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Left
                    )
                    Text(
                        text = "4.5 km away",
                        color = Color.White,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Left
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun HomeScreenComponentPreview() {
    HomeScreenComponent()
}
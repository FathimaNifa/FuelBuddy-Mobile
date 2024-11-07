package com.nifa.fuel_buddy.presentation.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R

@Composable
fun OrderApprovedScreen(modifier: Modifier = Modifier, title: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .background(colorResource(R.color.black)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(60.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(30.dp)
        )
        Spacer(modifier = modifier.height(120.dp))
        Image(
            painter = painterResource(R.drawable.approved),
            contentDescription = "Profile Image",
            modifier = modifier
                .padding(30.dp)
                .size(130.dp)
                .clip(CircleShape)
                .background(colorResource(R.color.black))
        )
        Spacer(modifier = modifier.height(150.dp))
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActionButton(
                "Track Order",
                colorResource(R.color.saffron),
                colorResource(R.color.black)
            ) {

            }
            ActionButton(
                "Back to Home",
                colorResource(R.color.raisin_black),
                colorResource(R.color.saffron)
            ) {

            }
        }
    }

}

@Composable
fun ActionButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(10.dp)
            .width(296.dp)
            .height(40.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                1.dp,
                colorResource(R.color.saffron),
                RoundedCornerShape(7.dp)
            ),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderApprovedScreenPreview() {
    OrderApprovedScreen(
        modifier = Modifier,
        title = "Your Order is Accepted"
    )
}
package com.nifa.fuel_buddy.presentation.feature.account
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
@Composable
fun AccountScreenComponent() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
                .background(colorResource(R.color.raisin_black)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(R.drawable.account),
                contentDescription = "Profile Image",
                modifier = Modifier.padding(30.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.black))
            )

            Spacer(modifier = Modifier.height(60.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton("xyz@gmail.com",R.drawable.account) {

                }
                ActionButton("Change Password",R.drawable.account) {

                }
                ActionButton("Support and Feedback",R.drawable.account) {

                }
                ActionButton("Logout",R.drawable.account) {

                }
            }
        }
    }
@Composable
fun ActionButton(text: String, iconResId: Int,onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(10.dp)
            .width(296.dp)
            .border(
                2.dp, colorResource(R.color.saffron),
                RoundedCornerShape(20.dp)
            ),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.raisin_black))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(iconResId),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.saffron),
                modifier = Modifier.weight(1f)
            )

        }
    }
}
@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    AccountScreenComponent()
}

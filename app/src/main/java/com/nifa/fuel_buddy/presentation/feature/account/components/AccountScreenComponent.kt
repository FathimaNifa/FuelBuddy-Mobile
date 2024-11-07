package com.nifa.fuel_buddy.presentation.feature.account.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun ActionButton(text: String, iconResId: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(10.dp)
            .width(296.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                2.dp, colorResource(R.color.saffron),
                RoundedCornerShape(8.dp)
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
                    .padding(end = 10.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
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

@Preview(showBackground = false)
@Composable
private fun AccountScreenPreview() {
    ActionButton(
        text = "xyz@gmail.com",
        iconResId = R.drawable.account,
        onClick = {}
    )
}

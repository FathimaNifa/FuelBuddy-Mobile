package com.nifa.fuel_buddy.fuelstation.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R

@Composable
fun AuthCTA(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.saffron),
            disabledContainerColor = colorResource(R.color.saffron).copy(alpha = .5f)
        ),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
private fun AuthCTAPreview() {
    AuthCTA(
        text = "Sign In",
        onClick = {},
        enabled = true
    )
}
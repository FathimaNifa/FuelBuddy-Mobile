package com.nifa.fuel_buddy.user.presentation.feature.home.components

import androidx.compose.foundation.BorderStroke
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
import com.nifa.fuel_buddy.core.utils.Font

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.saffron)
            ),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = textColor,
            fontFamily = Font.JosefinRegular,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    ActionButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Action",
        backgroundColor = colorResource(R.color.saffron),
        textColor = colorResource(R.color.black),
        onClick = {}
    )
}
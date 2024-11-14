package com.nifa.fuel_buddy.fuelstation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.fuelstation.presentation.composable.AuthCTA
import com.nifa.fuel_buddy.fuelstation.presentation.composable.AuthTextField

@Composable
fun DLVerificationScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(color = colorResource(R.color.black))
            .padding(30.dp),

        ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "DL VERIFICATION",
                color = colorResource(R.color.white),
                fontFamily = Font.JosefinBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )

            AuthTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = "Driving License Number",
                leadingIconResId = R.drawable.ic_license,
                onValueChange = {},
                value = ""
            )

            AuthTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = "Date of birth",
                leadingIconResId = R.drawable.ic_calendar,
                onValueChange = {},
                value = ""
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {

            AuthCTA(
                text = "Continue",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun DLVerificationScreenPreview() {
    DLVerificationScreen()
}
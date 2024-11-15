package com.nifa.fuel_buddy.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.presentation.composable.AuthCTA
import com.nifa.fuel_buddy.auth.presentation.composable.AuthTextField
import com.nifa.fuel_buddy.core.utils.Font

@Composable
fun FuelStationSignUpScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(color = colorResource(R.color.black))
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceAround

    ) {

        Text(
            text = "SIGN UP",
            color = colorResource(R.color.white),
            fontFamily = Font.JosefinBold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = "User Name",
            leadingIconResId = R.drawable.ic_account,
            onValueChange = {},
            value = ""
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = "Email Id",
            leadingIconResId = R.drawable.ic_mail,
            onValueChange = {},
            value = ""
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = "Registration Number",
            leadingIconResId = R.drawable.ic_check,
            onValueChange = {},
            value = ""
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = "Create New Password",
            leadingIconResId = R.drawable.ic_lock,
            onValueChange = {},
            value = ""
        )

        AuthTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = "Confirm Password",
            leadingIconResId = R.drawable.ic_lock,
            onValueChange = {},
            value = ""
        )


        AuthCTA(
            text = "Sign In",
            onClick = {}
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "I’m a already member ",
                fontSize = 14.sp,
                fontFamily = Font.JosefinRegular,
                color = colorResource(R.color.white),
                textAlign = TextAlign.Center
            )
            Text(
                text = "SIGN IN",
                fontSize = 14.sp,
                fontFamily = Font.JosefinRegular,
                color = colorResource(R.color.saffron),
                textAlign = TextAlign.Center,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

@Preview
@Composable
private fun FuelStationSignUpScreenPreview() {
    FuelStationSignUpScreen()
}
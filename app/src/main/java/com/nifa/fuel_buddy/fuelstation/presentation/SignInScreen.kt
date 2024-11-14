package com.nifa.fuel_buddy.fuelstation.presentation

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
import com.nifa.fuel_buddy.core.utils.Font
import com.nifa.fuel_buddy.fuelstation.presentation.composable.AuthCTA
import com.nifa.fuel_buddy.fuelstation.presentation.composable.AuthTextField

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(color = colorResource(R.color.black))
            .padding(30.dp),

        ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SIGN IN",
                color = colorResource(R.color.white),
                fontFamily = Font.JosefinBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        }


        Column(
            modifier = Modifier.weight(3f),
            verticalArrangement = Arrangement.SpaceAround
        ) {


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
                label = "Password",
                leadingIconResId = R.drawable.ic_lock,
                onValueChange = {},
                value = ""
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Forgot Password",
                fontSize = 14.sp,
                fontFamily = Font.JosefinRegular,
                color = colorResource(R.color.saffron),
                textAlign = TextAlign.Center,
                style = TextStyle(textDecoration = TextDecoration.Underline)
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
                    text = "I’m a new user ",
                    fontSize = 14.sp,
                    fontFamily = Font.JosefinRegular,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "SIGN UP",
                    fontSize = 14.sp,
                    fontFamily = Font.JosefinRegular,
                    color = colorResource(R.color.saffron),
                    textAlign = TextAlign.Center,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    SignInScreen()
}
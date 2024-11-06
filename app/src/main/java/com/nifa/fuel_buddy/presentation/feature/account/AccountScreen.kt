package com.nifa.fuel_buddy.presentation.feature.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.feature.account.components.ActionButton

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp)
            .background(colorResource(R.color.black)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .padding(30.dp)
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
            ActionButton("xyz@gmail.com", R.drawable.account) {

            }
            ActionButton("Change Password", R.drawable.chngpwd) {

            }
            ActionButton("Support and Feedback", R.drawable.support) {

            }
            ActionButton("Logout", R.drawable.logout) {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    AccountScreen(Modifier)
}
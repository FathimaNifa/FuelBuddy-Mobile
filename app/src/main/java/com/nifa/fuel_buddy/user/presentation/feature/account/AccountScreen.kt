package com.nifa.fuel_buddy.user.presentation.feature.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.user.presentation.feature.account.components.ActionButton

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    uiState: AccountScreenUiState,
    uiAction: (AccountScreenUiAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.black)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .padding(30.dp)
                .size(150.dp)
                .background(colorResource(R.color.black))
                .weight(1f),
            alignment = Alignment.TopEnd
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            ActionButton(
                text = uiState.userEmail,
                iconResId = R.drawable.ic_mail,
                onClick = {}
            )
            ActionButton(
                text = stringResource(R.string.change_password),
                iconResId = R.drawable.ic_key,
                onClick = { uiAction.invoke(AccountScreenUiAction.OnChangePasswordClicked) }
            )
            ActionButton(text = stringResource(R.string.support_and_feedback),
                iconResId = R.drawable.ic_support,
                onClick = { uiAction.invoke(AccountScreenUiAction.OnSupportAndFeedBackClicked) }
            )
            ActionButton(
                text = stringResource(R.string.logout),
                iconResId = R.drawable.ic_logout,
                onClick = { uiAction.invoke(AccountScreenUiAction.OnLogoutButtonClicked) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    AccountScreen(
        uiState = AccountScreenUiState(
            userEmail = "xyz@gmail.com"
        ),
        uiAction = {}
    )
}
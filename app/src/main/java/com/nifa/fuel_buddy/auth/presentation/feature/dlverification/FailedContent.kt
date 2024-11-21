package com.nifa.fuel_buddy.auth.presentation.feature.dlverification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.auth.presentation.feature.composable.AuthCTA
import com.nifa.fuel_buddy.core.utils.Font

@Composable
fun FailedContent(
    errorMessage: String,
    onGoBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.black)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.error),
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = Font.JosefinBold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_error),
                contentDescription = "Profile Image",
                modifier = modifier
                    .padding(30.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.black))
            )
        }

        Column {
            Text(
                text = errorMessage,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = Font.JosefinBold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )

            AuthCTA(
                modifier = Modifier.padding(30.dp),
                text = stringResource(R.string.go_back),
                onClick = onGoBackButtonClicked
            )
        }
    }
}

@Preview
@Composable
private fun FailedContentPreview() {
    FailedContent(
        errorMessage = "User Already Exists",
        onGoBackButtonClicked = {}
    )
}
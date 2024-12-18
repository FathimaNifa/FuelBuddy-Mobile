package com.nifa.fuel_buddy.core.presentation.userFeedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font

@Composable
fun FeedbackTextField(
    modifier: Modifier = Modifier,
    maxTextCount: Int,
    currentTextCount: Int,
    text: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)
        ) {
            Text(
                text = "$currentTextCount",
                color = colorResource(R.color.saffron),
                fontSize = 14.sp,
                fontFamily = Font.JosefinBold
            )

            Text(
                text = stringResource(R.string.slash),
                color = colorResource(R.color.saffron),
                fontSize = 14.sp,
                fontFamily = Font.JosefinBold
            )

            Text(
                text = "$maxTextCount",
                color = colorResource(R.color.saffron),
                fontSize = 14.sp,
                fontFamily = Font.JosefinBold
            )
        }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = text,
            onValueChange = onTextChange,
            textStyle = TextStyle(
                fontFamily = Font.JosefinRegular
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.saffron),
                unfocusedBorderColor = colorResource(R.color.saffron).copy(alpha = 0.5f),
                focusedContainerColor = colorResource(R.color.black),
                unfocusedContainerColor = colorResource(R.color.black),
                focusedLabelColor = colorResource(R.color.saffron),
                unfocusedLabelColor = colorResource(R.color.saffron),
                focusedTextColor = colorResource(R.color.saffron),
                unfocusedTextColor = colorResource(R.color.saffron),
                focusedLeadingIconColor = colorResource(R.color.saffron),
                unfocusedLeadingIconColor = colorResource(R.color.saffron),
                focusedTrailingIconColor = colorResource(R.color.saffron),
                unfocusedTrailingIconColor = colorResource(R.color.saffron),
                cursorColor = colorResource(R.color.saffron),
                errorTextColor = Color.Red,
                errorLabelColor = Color.Red,
                errorBorderColor = Color.Red,
                errorLeadingIconColor = colorResource(R.color.saffron),
                errorTrailingIconColor = colorResource(R.color.saffron),
            ),
            label = {
                Text(
                    text = "Share your Feed back",
                    fontFamily = Font.JosefinRegular,
                )
            },
        )
    }
}


@Preview
@Composable
private fun FeedbackTextFieldPreview() {
    FeedbackTextField(
        maxTextCount = 500,
        currentTextCount = 19,
        text = "We are Delight",
        onTextChange = {}
    )
}
package com.nifa.fuel_buddy.fuelstation.presentation.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    maskText: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    @DrawableRes
    leadingIconResId: Int? = null,
    @DrawableRes
    trailingIconResId: Int? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        textStyle = TextStyle(
            fontFamily = Font.JosefinRegular
        ),
        isError = isError,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (maskText) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.saffron),
            unfocusedBorderColor = colorResource(R.color.saffron).copy(alpha = 0.5f),
            focusedContainerColor = colorResource(R.color.raisin_black),
            unfocusedContainerColor = colorResource(R.color.raisin_black),
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
            errorLeadingIconColor = Color.Red,
            errorTrailingIconColor = Color.Red,
        ),
        label = {
            Text(
                text = label,
                modifier = Modifier.padding(8.dp),
                fontFamily = Font.JosefinRegular,
            )
        },
        leadingIcon = leadingIconResId?.let {
            @Composable {
                Icon(
                    imageVector = ImageVector.vectorResource(it),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onLeadingIconClick?.invoke() }
                        .size(22.dp)
                )
            }
        },
        trailingIcon = trailingIconResId?.let {
            @Composable {
                Icon(
                    imageVector = ImageVector.vectorResource(it),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onTrailingIconClick?.invoke() }
                        .size(22.dp)
                )
            }
        }
    )
}


@Preview
@Composable
private fun AuthTextFieldPreview() {
    AuthTextField(
        label = "Email",
        value = "dgdg",
        maskText = false,
        onValueChange = {},
        leadingIconResId = R.drawable.ic_mail,
        trailingIconResId = R.drawable.ic_key,
        isError = false
    )
}
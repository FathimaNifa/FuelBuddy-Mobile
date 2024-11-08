package com.nifa.fuel_buddy.presentation.feature.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.utils.Font

@Composable
fun ViewCartBottomCTA(
    modifier: Modifier = Modifier,
    addedItemCount: Int,
    visibility: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(
            initialOffsetY = { it }  // Slide in from the bottom
        ),
        exit = slideOutVertically(
            targetOffsetY = { it }  // Slide out to the bottom
        )
    ) {
        BottomSheetCTA(
            modifier = modifier.clickable(onClick = onClick)
        ) {
            Text(
                color = colorResource(R.color.black),
                fontSize = 14.sp,
                fontFamily = Font.JosefinSemiBold,
                modifier = Modifier.padding(20.dp),
                text = if (addedItemCount > 1)
                    stringResource(R.string.items_added, addedItemCount)
                else
                    stringResource(R.string.item_added, addedItemCount)
            )

            Row(
                modifier = Modifier
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.view_cart),
                    color = colorResource(R.color.black),
                    fontSize = 14.sp,
                    fontFamily = Font.JosefinSemiBold,

                    )
                Image(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ViewCartBottomCTAPreview() {
    ViewCartBottomCTA(
        addedItemCount = 5,
        onClick = {},
        visibility = true
    )
}
package com.nifa.fuel_buddy.user.presentation.feature.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.core.utils.Font

@Composable
fun AddQuantityComponent(
    modifier: Modifier = Modifier,
    quantity: Int,
    addButtonClicked: () -> Unit,
    removeButtonClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = colorResource(R.color.saffron).copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .animateContentSize(),
        contentAlignment = Alignment.Center
    ) {

        if (quantity > 0) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(10.dp)

            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_remove),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { removeButtonClicked.invoke() }
                        .padding(6.dp)

                )

                Text(
                    text = quantity.toString(),
                    fontSize = 12.sp,
                    fontFamily = Font.JosefinRegular,
                    color = colorResource(R.color.saffron),
                )

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { addButtonClicked.invoke() }
                        .padding(6.dp)
                )
            }

        } else {
            Text(
                text = stringResource(R.string.add),
                fontSize = 12.sp,
                fontFamily = Font.JosefinRegular,
                color = Color.White,
                modifier = Modifier
                    .clickable { addButtonClicked.invoke() }
                    .padding(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
            )
        }
    }

}

@Preview
@Composable
private fun AddQuantityComponentWithAddTextPreview() {
    AddQuantityComponent(
        quantity = 0,
        addButtonClicked = {},
        removeButtonClicked = {}
    )
}

@Preview
@Composable
private fun AddQuantityComponentWithPreview() {
    AddQuantityComponent(
        quantity = 5,
        addButtonClicked = {},
        removeButtonClicked = {}
    )
}
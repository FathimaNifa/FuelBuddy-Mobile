package com.nifa.fuel_buddy.presentation.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nifa.fuel_buddy.R
import com.nifa.fuel_buddy.presentation.utils.Font
import com.nifa.fuel_buddy.presentation.utils.ext.prependRupees
import com.nifa.fuel_buddy.presentation.utils.imagePainter

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl : String,
    price: Long,
    quantity: Int,
    addButtonClicked : () -> Unit,
    removeButtonClicked : () -> Unit
) {
    Row(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = colorResource(R.color.saffron).copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .background(colorResource(R.color.raisin_black))
            .padding(10.dp)
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = imagePainter(imageUrl),
                contentDescription = null,
            )

            Text(
                text = name,
                fontSize = 12.sp,
                fontFamily = Font.JosefinRegular,
                color = Color.White,
            )

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = price.toString().prependRupees(),
                fontSize = 12.sp,
                fontFamily = Font.JosefinRegular,
                color = Color.White,
            )

            AddQuantityComponent(
                quantity = quantity,
                addButtonClicked = addButtonClicked,
                removeButtonClicked = removeButtonClicked
            )
        }
    }

}

@Preview
@Composable
private fun ProductCardPreview() {
    ProductCard(
        name = "Petrol",
        imageUrl = "",
        quantity = 0,
        price = 100,
        addButtonClicked = {},
        removeButtonClicked = {}
    )
}
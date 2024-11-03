package com.nifa.fuel_buddy.presentation.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeDetailScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("HomeDetailScreen")
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeDetailScreenPreview() {
    HomeDetailScreen()
}
package com.nifa.fuel_buddy.presentation.feature.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ActivityDetailScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("ActivityDetailScreen")
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityDetailScreenPreview() {
    ActivityDetailScreen()
}
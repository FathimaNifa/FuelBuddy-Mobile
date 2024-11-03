package com.nifa.fuel_buddy.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nifa.fuel_buddy.data.dummyFuelStationList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    uiAction: ((HomeScreenUiAction) -> Unit),
    uiEvent: Flow<HomeScreenUiEvent>
) {

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(uiState.fuelStationList.size) { index ->
            val data = uiState.fuelStationList[index]
            Text(text = data.name, color = Color.Blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeScreenUiState(
            fuelStationList = dummyFuelStationList
        ),
        uiAction = {},
        uiEvent = emptyFlow()
    )
}
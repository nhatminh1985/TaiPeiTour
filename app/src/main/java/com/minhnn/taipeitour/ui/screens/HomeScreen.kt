package com.minhnn.taipeitour.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.minhnn.taipeitour.data.entity.Data
import com.minhnn.taipeitour.ui.components.DetailScreen
import com.minhnn.taipeitour.ui.components.EmptyStateComponent
import com.minhnn.taipeitour.ui.components.Header
import com.minhnn.taipeitour.ui.components.ItemList
import com.minhnn.taipeitour.ui.components.Loader
import com.minhnn.taipeitour.ui.components.WebViewScreen
import com.minhnn.taipeitour.ui.viewmodel.AttractionsViewModel
import com.minhnn.utitlities1.ResourcesState

const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    attractionsViewModel: AttractionsViewModel = hiltViewModel()
) {

    val attractionRes by attractionsViewModel.attractions.collectAsState()

    when (attractionRes) {
        is ResourcesState.Loading -> {
            Loader()
        }

        is ResourcesState.Success -> {
            val response = (attractionRes as ResourcesState.Success).data

            if (response.data.isNotEmpty()) {
                Column {
                    var selectedItem by remember { mutableStateOf<Data?>(null) }
                    var webUrl by remember { mutableStateOf<String?>(null) }

                    when {
                        webUrl != null -> {
                            WebViewScreen(url = webUrl!!, navigateBack = { webUrl = null })
                        }

                        selectedItem != null -> {
                            DetailScreen(
                                data = selectedItem!!,
                                navigateBack = { selectedItem = null },
                                onUrlClick = { url -> webUrl = url }
                            )
                        }

                        else -> {
                            Header()
                            ItemList(response.data) {
                                selectedItem = it
                            }
                        }
                    }
                }
            } else {
                EmptyStateComponent()
            }
        }

        is ResourcesState.Error -> {
            val error = (attractionRes as ResourcesState.Error).error
        }
    }
}
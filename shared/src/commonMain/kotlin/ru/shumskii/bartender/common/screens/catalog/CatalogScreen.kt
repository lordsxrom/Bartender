package ru.shumskii.bartender.common.screens.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.shumskii.bartender.common.ui.AsyncImage


@Composable
fun CatalogScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Catalog")
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            ViewModel(factory = { CatalogViewModel() }) { viewModel ->
                val viewState = viewModel.viewStates().observeAsState()
                when (val state = viewState.value) {
                    is CatalogViewState.Idle -> {

                    }

                    is CatalogViewState.Data -> {
                        val lazyColumnListState = rememberLazyListState()
                        val shouldStartPaginate = remember {
                            derivedStateOf {
                                (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
                            }
                        }
                        LazyColumn(state = lazyColumnListState) {
                            items(state.drinks) { drinkVo ->
                                Column {
                                    IconToggleButton(
                                        checked = drinkVo.isFavourite,
                                        onCheckedChange = {
                                            viewModel.obtainEvent(
                                                CatalogEvent.SetFavouriteDrink(
                                                    drinkVo.id
                                                )
                                            )
                                        },
                                    ) {
                                        Icon(
                                            Icons.Filled.Favorite,
                                            contentDescription = "Favourite",
                                            tint = if (drinkVo.isFavourite) {
                                                Color.Red
                                            } else {
                                                Color.LightGray
                                            }
                                        )
                                    }

                                    AsyncImage(
                                        url = drinkVo.drinkThumb!!,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )

                                    Text(
                                        text = drinkVo.drink ?: "drink!!",
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                        LaunchedEffect(key1 = shouldStartPaginate.value) {
                            viewModel.obtainEvent(CatalogEvent.NextBatchLoading)
                        }
                    }

                    is CatalogViewState.Error -> {
                        Text(
                            text = state.errorMessage,
                        )
                    }

                    is CatalogViewState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                val viewAction = viewModel.viewActions().observeAsState()
                viewAction.value?.let { action ->
//                when(action) {
//
//                }
                }

                LaunchedEffect(Unit) {
                    viewModel.obtainEvent(CatalogEvent.StartLoading)
                }
            }
        }
    }
}
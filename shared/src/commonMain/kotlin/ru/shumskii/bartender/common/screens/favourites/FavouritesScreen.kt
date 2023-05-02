package ru.shumskii.bartender.common.screens.favourites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.shumskii.bartender.common.screens.catalog.CatalogEvent
import ru.shumskii.bartender.common.screens.catalog.CatalogViewState
import ru.shumskii.bartender.common.ui.AsyncImage

@Composable
fun FavouritesScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Favourites")
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val rootController = LocalRootController.current

            ViewModel(factory = { FavouritesViewModel() }) { viewModel ->
                val viewState = viewModel.viewStates().observeAsState()
                when (val state = viewState.value) {
                    is FavouritesViewState.Idle -> {

                    }

                    is FavouritesViewState.Data -> {
                        LazyColumn {
                            items(state.drinks) { drinkVo ->
                                Column {
                                    IconToggleButton(
                                        checked = drinkVo.isFavourite,
                                        onCheckedChange = {
                                            viewModel.obtainEvent(
                                                FavouritesEvent.SetFavouriteDrink(
                                                    drinkVo.id
                                                )
                                            )
                                        },
                                    ) {
                                        Icon(
                                            Icons.Filled.Favorite,
                                            contentDescription = "Информация о приложении",
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
                    }

                    is FavouritesViewState.Error -> {
                        Text(
                            text = state.errorMessage,
                        )
                    }

                    is FavouritesViewState.Loading -> {
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
                    when (action) {
                        is FavouritesAction.OpenDrinkScreen -> {
                            rootController.push("drink", action.navObject)
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    viewModel.obtainEvent(FavouritesEvent.StartLoading)
                }
            }
        }
    }
}
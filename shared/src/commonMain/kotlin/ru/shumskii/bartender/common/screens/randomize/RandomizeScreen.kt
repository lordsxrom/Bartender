package ru.shumskii.bartender.common.screens.randomize

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.shumskii.bartender.common.ui.AsyncImage

@Composable
fun RandomizeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Randomize")
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                elevation = 12.dp
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.surface
        ) {
            val rootController = LocalRootController.current

            ViewModel(factory = { RandomizeViewModel() }) { viewModel ->
                val viewState = viewModel.viewStates().observeAsState()
                when (val state = viewState.value) {
                    is RandomizeViewState.Data -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        viewModel.obtainEvent(
                                            RandomizeEvent.DrinkClick(
                                                id = state.vo.id,
                                                drink = state.vo.drink ?: "",
                                            )
                                        )
                                    })
                            ) {
                                AsyncImage(
                                    url = state.vo.drinkThumb!!,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )

                                Text(
                                    text = state.vo.drink ?: "drink",
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                )
                            }

                            RandomizeButton(
                                onClick = { viewModel.obtainEvent(RandomizeEvent.RandomizeClick) },
                                enabled = true,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )

                            IconToggleButton(
                                checked = state.vo.isFavourite,
                                onCheckedChange = {
                                    viewModel.obtainEvent(
                                        RandomizeEvent.FavouriteDrinkClick(state.vo.id)
                                    )
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(all = 16.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Favourite",
                                    tint = if (state.vo.isFavourite) {
                                        Color.Red
                                    } else {
                                        Color.LightGray
                                    }
                                )
                            }
                        }
                    }

                    is RandomizeViewState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = state.errorMessage,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 16.dp)
                            )

                            RandomizeButton(
                                onClick = { viewModel.obtainEvent(RandomizeEvent.RandomizeClick) },
                                enabled = true,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }

                    RandomizeViewState.Idle -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "Click and randomize your drink",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 16.dp)
                            )

                            RandomizeButton(
                                onClick = { viewModel.obtainEvent(RandomizeEvent.RandomizeClick) },
                                enabled = true,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }

                    RandomizeViewState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )

                            Button(
                                onClick = { viewModel.obtainEvent(RandomizeEvent.RandomizeClick) },
                                content = {
                                    Text("Randomize")
                                },
                                enabled = false,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                            )
                            RandomizeButton(
                                onClick = { viewModel.obtainEvent(RandomizeEvent.RandomizeClick) },
                                enabled = false,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                val viewAction = viewModel.viewActions().observeAsState()
                viewAction.value?.let { action ->
                    when (action) {
                        is RandomizeAction.OpenDrinkScreen -> {
                            rootController.push("drink", action.navObject)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RandomizeButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        content = {
            Text("Randomize")
        },
        enabled = enabled,
        modifier = modifier,
    )
}

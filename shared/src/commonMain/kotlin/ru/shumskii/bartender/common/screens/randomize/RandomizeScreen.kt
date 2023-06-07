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
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.adeo.kviewmodel.odyssey.setupWithViewModels
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.shumskii.bartender.common.AppRes

@Composable
fun RandomizeScreen() {
    val rootController = LocalRootController.current
    rootController.setupWithViewModels()

    StoredViewModel(factory = { RandomizeViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState()
        when (val state = viewState.value) {
            is RandomizeViewState.Data -> {
                RandomizeScreenData(
                    state = state,
                    onDrinkClicked = { randomizeDrinkVo ->
                        viewModel.obtainEvent(
                            RandomizeEvent.DrinkClick(
                                id = randomizeDrinkVo.id,
                                drink = randomizeDrinkVo.drink ?: "",
                            )
                        )
                    },
                    onRandomizeButtonClicked = {
                        viewModel.obtainEvent(RandomizeEvent.RandomizeClick)
                    },
                    onFavouriteDrinkClicked = { randomizeDrinkVo ->
                        viewModel.obtainEvent(
                            RandomizeEvent.FavouriteDrinkClick(randomizeDrinkVo.id)
                        )
                    }
                )
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
                        text = AppRes.string.randomize_screen_welcome_message,
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
                RandomizeScreenLoading(
                    onRandomizeButtonClicked = {
                        viewModel.obtainEvent(RandomizeEvent.RandomizeClick)
                    },
                )
            }
        }

        val viewAction = viewModel.viewActions().observeAsState()
        viewAction.value?.let { action ->
            when (action) {
                is RandomizeAction.OpenDrinkScreen -> {
                    rootController.push("drink", action.navObject)
                    viewModel.obtainEvent(RandomizeEvent.ActionInvoked)
                }
            }
        }
    }
}

@Composable
fun RandomizeScreenData(
    state: RandomizeViewState.Data,
    onDrinkClicked: (RandomizeDrinkVo) -> Unit,
    onRandomizeButtonClicked: () -> Unit,
    onFavouriteDrinkClicked: (RandomizeDrinkVo) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = AppRes.string.randomize_screen_toolbar_title)
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                elevation = 12.dp
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colors.surface,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .clickable(onClick = { onDrinkClicked(state.vo) })
                ) {
                    KamelImage(
                        resource = lazyPainterResource(data = state.vo.drinkThumb!!),
                        contentDescription = "Drink",
                        onLoading = { CircularProgressIndicator(it) },
                        onFailure = { exception ->
                            Text(text = exception.message!!)
                        },
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
                    onClick = onRandomizeButtonClicked,
                    enabled = true,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                IconToggleButton(
                    checked = state.vo.isFavourite,
                    onCheckedChange = { onFavouriteDrinkClicked(state.vo) },
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
    }
}

@Composable
fun RandomizeScreenLoading(
    onRandomizeButtonClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = AppRes.string.randomize_screen_toolbar_title)
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                elevation = 12.dp
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colors.surface,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )

                RandomizeButton(
                    onClick = onRandomizeButtonClicked,
                    enabled = false,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
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
            Text(AppRes.string.randomize_screen_randomize_button_text)
        },
        enabled = enabled,
        modifier = modifier,
    )
}

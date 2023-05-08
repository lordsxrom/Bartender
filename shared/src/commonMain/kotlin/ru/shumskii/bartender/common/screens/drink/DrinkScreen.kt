package ru.shumskii.bartender.common.screens.drink

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.adeo.kviewmodel.odyssey.setupWithViewModels
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.shumskii.bartender.common.ui.AsyncImage


@Composable
fun DrinkScreen(navObject: DrinkScreenNavObject) {

    val rootController = LocalRootController.current
    rootController.setupWithViewModels()

    StoredViewModel(factory = { DrinkViewModel() }) { viewModel ->

        val viewState = viewModel.viewStates().observeAsState()
        when (val state = viewState.value) {
            is DrinkViewState.Idle -> {

            }

            is DrinkViewState.Data -> {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = navObject.drink)
                            },
                            backgroundColor = Color.Blue,
                            contentColor = Color.White,
                            elevation = 12.dp,
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        rootController.popBackStack()
                                    },
                                ) {
                                    Icon(Icons.Filled.ArrowBack, "")
                                }
                            },
                            actions = {
                                IconToggleButton(
                                    checked = state.vo.isFavourite,
                                    onCheckedChange = {
                                        viewModel.obtainEvent(
                                            DrinkEvent.SetFavouriteDrink(state.vo.id)
                                        )
                                    },
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
                            },
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Column {
                            AsyncImage(
                                url = state.vo.drinkThumb!!,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            Text(
                                text = state.vo.drink ?: "drink!!",
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            )

                            Text(
                                text = state.vo.alcoholic ?: "alcoholic!!",
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }

            is DrinkViewState.Error -> {
                Text(
                    text = state.errorMessage,
                )
            }

            is DrinkViewState.Loading -> {
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
                DrinkAction.CloseScreen -> rootController.popBackStack()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.obtainEvent(DrinkEvent.StartLoading(navObject.id))
        }
    }
}
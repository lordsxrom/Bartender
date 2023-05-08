package ru.shumskii.bartender.common.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import com.adeo.kviewmodel.odyssey.setupWithViewModels
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Settings")
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
            rootController.setupWithViewModels()

            val modalController = rootController.findModalController()

            StoredViewModel(factory = { SettingsViewModel() }) { viewModel ->
                val viewState = viewModel.viewStates().observeAsState()
                when (val state = viewState.value) {
                    is SettingsViewState.Data -> {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = 16.dp)
                            ) {
                                Text("Dark mode")
                                Switch(
                                    checked = state.vo.isDarkModeEnabled,
                                    onCheckedChange = {
                                        viewModel.obtainEvent(
                                            SettingsEvent.SetDarkModeEnabled(
                                                !state.vo.isDarkModeEnabled
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                )
                            }

                        }
                    }

                    is SettingsViewState.Error -> TODO()
                    SettingsViewState.Idle -> {

                    }

                    SettingsViewState.Loading -> {
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
                when (val action = viewAction.value) {
                    is SettingsAction.ShowReloadDialog -> {
                        val alertConfiguration = AlertConfiguration(
                            maxHeight = 0.4f,
                            maxWidth = 0.8f,
                            cornerRadius = 4,
                        )
                        modalController.present(alertConfiguration) { key ->
                            ReloadDialogScreen {
                                modalController.popBackStack(key)
                            }
                        }
                    }

                    null -> {}
                }

                LaunchedEffect(Unit) {
                    viewModel.obtainEvent(SettingsEvent.StartLoading)
                }
            }
        }
    }
}
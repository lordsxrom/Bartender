package ru.shumskii.bartender.common

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.shumskii.bartender.common.navigation.navigationGraph
import ru.shumskii.bartender.common.ui.BartenderTheme

@Composable
fun MainView(activity: ComponentActivity) {
    BartenderTheme {
        val odysseyConfiguration = OdysseyConfiguration(
            canvas = activity,
            startScreen = StartScreen.Custom("main")
        )

        setNavigationContent(odysseyConfiguration, onApplicationFinish = {
            activity.finishAffinity()
        }) {
            navigationGraph()
        }
    }
}



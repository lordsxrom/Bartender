package ru.shumskii.bartender.common.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomBarDefaults
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabDefaults
import ru.shumskii.bartender.common.screens.catalog.CatalogScreen
import ru.shumskii.bartender.common.screens.drink.DrinkScreen
import ru.shumskii.bartender.common.screens.drink.DrinkScreenNavObject
import ru.shumskii.bartender.common.screens.favourites.FavouritesScreen
import ru.shumskii.bartender.common.screens.randomize.RandomizeScreen
import ru.shumskii.bartender.common.screens.settings.SettingsScreen

@Composable
fun RootComposeBuilder.navigationGraph() {

    bottomNavigation(
        name = "main",
        colors = BottomBarDefaults.bottomColors(
            backgroundColor = MaterialTheme.colors.primary
        )
    ) {
        val colors = TabDefaults.simpleTabColors(
            selectedColor = MaterialTheme.colors.secondary,
            unselectedColor = MaterialTheme.colors.onPrimary
        )

        tab(
            content = TabDefaults.content("Randomize", Icons.Filled.Refresh),
            colors = colors,
        ) {
            screen("randomize") { RandomizeScreen() }

            screen("drink") {
                DrinkScreen(
                    navObject = it as DrinkScreenNavObject,
                )
            }
        }

        tab(
            content = TabDefaults.content("Catalog", Icons.Filled.List),
            colors = colors,
        ) {
            screen("catalog") { CatalogScreen() }

        }

        tab(
            content = TabDefaults.content("Favourites", Icons.Filled.Favorite),
            colors = colors,
        ) {
            screen("favourites") { FavouritesScreen() }

        }

        tab(
            content = TabDefaults.content("Settings", Icons.Filled.Settings),
            colors = colors,
        ) {
            screen("settings") { SettingsScreen() }

        }


    }
}
package ru.shumskii.bartender.common.screens.randomize

import ru.shumskii.bartender.common.screens.drink.DrinkScreenNavObject

sealed class RandomizeEvent {
    object RandomizeClick : RandomizeEvent()

    data class DrinkClick(
        val id: Long,
        val drink: String,
    ) : RandomizeEvent()

    data class FavouriteDrinkClick(
        val id: Long,
    ) : RandomizeEvent()
}

sealed class RandomizeAction {
    data class OpenDrinkScreen(
        val navObject: DrinkScreenNavObject,
    ) : RandomizeAction()
}

sealed class RandomizeViewState {
    object Idle : RandomizeViewState()

    object Loading : RandomizeViewState()

    data class Data(
        val vo: RandomizeDrinkVo
    ) : RandomizeViewState()

    data class Error(
        val errorMessage: String
    ) : RandomizeViewState()
}
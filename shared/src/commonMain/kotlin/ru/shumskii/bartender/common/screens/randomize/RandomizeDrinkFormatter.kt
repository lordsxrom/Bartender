package ru.shumskii.bartender.common.screens.randomize

import ru.shumskii.bartender.common.domain.model.Drink

class RandomizeDrinkFormatter {

    fun format(
        drink: Drink,
        isFavourite: Boolean,
    ): RandomizeDrinkVo {
        return RandomizeDrinkVo(
            id = drink.id,
            drink = drink.drink,
            drinkThumb = drink.drinkThumb,
            alcoholic = drink.alcoholic,
            isFavourite = isFavourite,
        )
    }

}
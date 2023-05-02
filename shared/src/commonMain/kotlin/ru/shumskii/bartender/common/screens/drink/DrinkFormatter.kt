package ru.shumskii.bartender.common.screens.drink

import ru.shumskii.bartender.common.domain.model.Drink

class DrinkFormatter {

    fun format(
        drink: Drink,
        isFavourite: Boolean,
    ): DrinkVo {
        return DrinkVo(
            id = drink.id,
            drink = drink.drink,
            drinkThumb = drink.drinkThumb,
            alcoholic = drink.alcoholic,
            isFavourite = isFavourite,
        )
    }

}
package ru.shumskii.bartender.common.screens.catalog

import ru.shumskii.bartender.common.domain.model.Drink

class CatalogDrinkFormatter {

    fun format(
        drink: Drink,
        isFavourite: Boolean,
    ): CatalogDrinkVo {
        return CatalogDrinkVo(
            id = drink.id,
            drink = drink.drink ?: "",
            drinkThumb = drink.drinkThumb ?: "",
            isFavourite = isFavourite
        )
    }

}
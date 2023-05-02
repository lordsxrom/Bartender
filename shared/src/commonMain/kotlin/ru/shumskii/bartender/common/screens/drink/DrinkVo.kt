package ru.shumskii.bartender.common.screens.drink

data class DrinkVo(
    val id: Long,
    val drink: String?,
    val drinkThumb: String?,
    val alcoholic: String?,
    val isFavourite: Boolean,
)

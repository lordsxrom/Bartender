package ru.shumskii.bartender.common.screens.randomize

data class RandomizeDrinkVo(
    val id: Long,
    val drink: String?,
    val drinkThumb: String?,
    val alcoholic: String?,
    val isFavourite: Boolean,
)
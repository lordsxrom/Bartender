package ru.shumskii.bartender.common.screens.catalog

data class CatalogDrinkVo(
    val id: Long,
    val drink: String?,
    val drinkThumb: String?,
    val isFavourite: Boolean,
)
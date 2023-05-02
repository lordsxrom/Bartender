package ru.shumskii.bartender.common.domain.model

data class Drink(
    val id: Long,
    val instructions: String?,
    val dateModified: String?,
    val alcoholic: String?,
    val category: String?,
    val creativeCommonsConfirmed: String?,
    val drink: String?,
    val drinkAlternate: String?,
    val drinkThumb: String?,
    val glass: String?,
    val iba: String?,
    val imageAttribution: String?,
    val imageSource: String?,
    val measuredIngredients: List<MeasuredIngredient>,
    val tags: String?,
    val video: String?
)
package ru.shumskii.bartender.common.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
internal data class DrinksResponse(
    val drinks: List<DrinkDto>,
)
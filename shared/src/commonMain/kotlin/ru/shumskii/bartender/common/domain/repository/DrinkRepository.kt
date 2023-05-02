package ru.shumskii.bartender.common.domain.repository

import ru.shumskii.bartender.common.domain.model.Drink

interface DrinkRepository {

    suspend fun getRandomDrink(): Drink

    suspend fun getDrinkById(id: Long): Drink

    suspend fun getDrinkByIds(ids: List<Long>): List<Drink>

    suspend fun getDrinksByFirstLetter(letter: Char): List<Drink>

}
package ru.shumskii.bartender.common.data.remote

import kotlinx.coroutines.withContext
import ru.shumskii.bartender.common.util.Dispatcher

internal class DrinkRemoteDataSource(
    private val apiService: DrinkService,
    private val dispatcher: Dispatcher
) {

    suspend fun getRandomDrink() = withContext(dispatcher.io) {
        apiService.getRandomDrink()
    }

    suspend fun getDrink(id: Long) = withContext(dispatcher.io) {
        apiService.getDrink(id)
    }

    suspend fun getDrinksByFirstLetter(letter: Char) = withContext(dispatcher.io) {
        apiService.getDrinksByFirstLetter(letter)
    }

}
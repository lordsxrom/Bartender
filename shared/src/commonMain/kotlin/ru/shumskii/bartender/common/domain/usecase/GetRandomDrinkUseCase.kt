package ru.shumskii.bartender.common.domain.usecase

import ru.shumskii.bartender.common.domain.model.Drink
import ru.shumskii.bartender.common.domain.repository.DrinkRepository

class GetRandomDrinkUseCase constructor(
    private val repository: DrinkRepository
) {
    @Throws(Exception::class)
    suspend fun execute(): Drink {
        return repository.getRandomDrink()
    }

}
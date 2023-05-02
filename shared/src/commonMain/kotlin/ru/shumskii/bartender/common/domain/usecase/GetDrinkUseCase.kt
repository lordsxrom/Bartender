package ru.shumskii.bartender.common.domain.usecase

import ru.shumskii.bartender.common.domain.model.Drink
import ru.shumskii.bartender.common.domain.repository.DrinkRepository

class GetDrinkUseCase constructor(
    private val repository: DrinkRepository
) {
    @Throws(Exception::class)
    suspend fun execute(id: Long): Drink {
        return repository.getDrinkById(id)
    }

}
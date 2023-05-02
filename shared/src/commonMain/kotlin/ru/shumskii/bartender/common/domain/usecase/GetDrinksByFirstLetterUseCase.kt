package ru.shumskii.bartender.common.domain.usecase

import ru.shumskii.bartender.common.domain.model.Drink
import ru.shumskii.bartender.common.domain.repository.DrinkRepository

class GetDrinksByFirstLetterUseCase constructor(
    private val repository: DrinkRepository
) {
    @Throws(Exception::class)
    suspend fun execute(letter: Char): List<Drink> {
        return repository.getDrinksByFirstLetter(letter)
    }

}
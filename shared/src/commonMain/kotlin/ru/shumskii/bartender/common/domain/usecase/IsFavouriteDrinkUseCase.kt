package ru.shumskii.bartender.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.shumskii.bartender.common.domain.repository.FavouriteRepository

class IsFavouriteDrinkUseCase constructor(
    private val favouriteRepository: FavouriteRepository,
) {

    suspend fun execute(id: Long): Flow<Boolean> {
        return favouriteRepository.isFavouriteDrink(id)
    }

}
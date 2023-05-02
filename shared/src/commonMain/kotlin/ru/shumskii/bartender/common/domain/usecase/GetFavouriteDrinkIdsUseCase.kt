package ru.shumskii.bartender.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.shumskii.bartender.common.domain.repository.FavouriteRepository

class GetFavouriteDrinkIdsUseCase constructor(
    private val favouriteRepository: FavouriteRepository,
) {

    suspend fun execute(): Flow<List<Long>> {
        return favouriteRepository.getFavouriteDrinkIds()
    }

}
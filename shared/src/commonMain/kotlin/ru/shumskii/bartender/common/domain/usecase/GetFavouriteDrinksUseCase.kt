package ru.shumskii.bartender.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import ru.shumskii.bartender.common.domain.model.Drink
import ru.shumskii.bartender.common.domain.repository.DrinkRepository
import ru.shumskii.bartender.common.domain.repository.FavouriteRepository

class GetFavouriteDrinksUseCase constructor(
    private val favouriteRepository: FavouriteRepository,
    private val drinkRepository: DrinkRepository,
) {

    suspend fun execute(): Flow<List<Drink>> {
        return favouriteRepository.getFavouriteDrinkIds().map { favouriteDrinkIds ->
            drinkRepository.getDrinkByIds(favouriteDrinkIds)
        }
    }

}
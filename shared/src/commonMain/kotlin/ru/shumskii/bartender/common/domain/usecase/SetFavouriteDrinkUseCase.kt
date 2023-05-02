package ru.shumskii.bartender.common.domain.usecase

import ru.shumskii.bartender.common.domain.repository.FavouriteRepository

class SetFavouriteDrinkUseCase constructor(
    private val favouriteRepository: FavouriteRepository,
) {

    suspend fun execute(id: Long) {
        favouriteRepository.setFavouriteDrinkId(id)
    }

}
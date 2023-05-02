package ru.shumskii.bartender.common.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    suspend fun setFavouriteDrinkId(id: Long)

    suspend fun getFavouriteDrinkIds(): Flow<List<Long>>

    suspend fun isFavouriteDrink(id: Long): Flow<Boolean>

}
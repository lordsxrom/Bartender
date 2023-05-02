package ru.shumskii.bartender.common.data.repository

import Database
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.shumskii.bartender.common.domain.repository.FavouriteRepository
import ru.shumskii.bartender.common.util.Dispatcher

class FavouriteRepositoryImpl(
    private val database: Database,
    private val dispatcher: Dispatcher,
): FavouriteRepository {

    override suspend fun setFavouriteDrinkId(id: Long) {
        val favouriteDrinkIds = database.favouriteDrinkQueries.selectAll().executeAsList()
        if (favouriteDrinkIds.contains(id)) {
            database.favouriteDrinkQueries.delete(id)
        } else {
            database.favouriteDrinkQueries.insert(id)
        }
    }

    override suspend fun getFavouriteDrinkIds(): Flow<List<Long>> {
        return database.favouriteDrinkQueries.selectAll().asFlow().mapToList(dispatcher.io)
    }

    override suspend fun isFavouriteDrink(id: Long): Flow<Boolean> {
        return database.favouriteDrinkQueries.selectById(id).asFlow().map { query ->
            query.executeAsOneOrNull() != null
        }
    }

}
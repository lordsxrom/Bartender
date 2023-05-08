package ru.shumskii.bartender.common.data.repository

import Database
import ru.shumskii.bartender.common.data.mapper.DrinkMapper
import ru.shumskii.bartender.common.data.remote.DrinkRemoteDataSource
import ru.shumskii.bartender.common.domain.model.Drink
import ru.shumskii.bartender.common.domain.repository.DrinkRepository

internal class DrinkRepositoryImpl(
    private val database: Database,
    private val remoteDateSource: DrinkRemoteDataSource,
    private val drinkMapper: DrinkMapper,
) : DrinkRepository {

    override suspend fun getRandomDrink(): Drink {
        val drinkDto = remoteDateSource.getRandomDrink().drinks.first()
        val drinkEntity = drinkMapper.mapDtoToEntity(drinkDto)
        database.drinkQueries.insert(drinkEntity)
        return drinkMapper.mapDtoToDomain(drinkDto)
    }

    override suspend fun getDrinkById(id: Long): Drink {
        val drinkLocalEntity = database.drinkQueries.selectById(id).executeAsOneOrNull()
        return if (drinkLocalEntity != null) {
            drinkMapper.mapEntityToDomain(drinkLocalEntity)
        } else {
            val drinkDto = remoteDateSource.getDrink(id).drinks.first()
            val drinkEntity = drinkMapper.mapDtoToEntity(drinkDto)
            database.drinkQueries.insert(drinkEntity)
            return drinkMapper.mapDtoToDomain(drinkDto)
        }
    }

    override suspend fun getDrinkByIds(ids: List<Long>): List<Drink> {
        val drinkLocalEntities = database.drinkQueries.selectByIds(ids).executeAsList()
        return drinkLocalEntities.map { drinkEntity -> drinkMapper.mapEntityToDomain(drinkEntity) }
    }

    override suspend fun getDrinksByFirstLetter(letter: Char): List<Drink> {
        val drinkEntitiesLocal = database.drinkQueries.selectAll().executeAsList().filter {
            val first = it.drink?.first()?.toLowerCase()
            first == letter
        }

        if (drinkEntitiesLocal.isNotEmpty()) {
            return drinkEntitiesLocal.map {
                drinkMapper.mapEntityToDomain(it)
            }
        }

        val drinkDtos = remoteDateSource.getDrinksByFirstLetter(letter).drinks
        val drinkEntities = drinkDtos.map { drinkDto -> drinkMapper.mapDtoToEntity(drinkDto) }
        database.transaction {
            drinkEntities.forEach { drinkEntity ->
                database.drinkQueries.insert(drinkEntity)
            }
        }
        return drinkDtos.map { drinkDto -> drinkMapper.mapDtoToDomain(drinkDto) }
    }

}
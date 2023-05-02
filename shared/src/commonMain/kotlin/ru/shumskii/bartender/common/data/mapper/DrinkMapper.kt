package ru.shumskii.bartender.common.data.mapper

import data.DrinkEntity
import ru.shumskii.bartender.common.data.remote.model.DrinkDto
import ru.shumskii.bartender.common.domain.model.Drink
import ru.shumskii.bartender.common.domain.model.MeasuredIngredient

internal class DrinkMapper {

    fun mapDtoToDomain(drinkDto: DrinkDto): Drink {
        return Drink(
            id = drinkDto.id,
            instructions = drinkDto.instructions,
            dateModified = drinkDto.dateModified,
            alcoholic = drinkDto.alcoholic,
            category = drinkDto.category,
            creativeCommonsConfirmed = drinkDto.creativeCommonsConfirmed,
            drink = drinkDto.drink,
            drinkAlternate = drinkDto.drinkAlternate,
            drinkThumb = drinkDto.drinkThumb,
            glass = drinkDto.glass,
            iba = drinkDto.iba,
            imageAttribution = drinkDto.imageAttribution,
            imageSource = drinkDto.imageSource,
            measuredIngredients = mapMeasuredIngredients(drinkDto),
            tags = drinkDto.tags,
            video = drinkDto.video,
        )
    }

    fun mapDtoToEntity(drinkDto: DrinkDto): DrinkEntity {
        return DrinkEntity(
            id = drinkDto.id,
            instructions = drinkDto.instructions,
            dateModified = drinkDto.dateModified,
            alcoholic = drinkDto.alcoholic,
            category = drinkDto.category,
            creativeCommonsConfirmed = drinkDto.creativeCommonsConfirmed,
            drink = drinkDto.drink,
            drinkAlternate = drinkDto.drinkAlternate,
            drinkThumb = drinkDto.drinkThumb,
            glass = drinkDto.glass,
            iba = drinkDto.iba,
            imageAttribution = drinkDto.imageAttribution,
            imageSource = drinkDto.imageSource,
            measuredIngredients = mapMeasuredIngredients(drinkDto),
            tags = drinkDto.tags,
            video = drinkDto.video,
        )
    }

    fun mapEntityToDomain(drinkEntity: DrinkEntity): Drink {
        return Drink(
            id = drinkEntity.id,
            instructions = drinkEntity.instructions,
            dateModified = drinkEntity.dateModified,
            alcoholic = drinkEntity.alcoholic,
            category = drinkEntity.category,
            creativeCommonsConfirmed = drinkEntity.creativeCommonsConfirmed,
            drink = drinkEntity.drink,
            drinkAlternate = drinkEntity.drinkAlternate,
            drinkThumb = drinkEntity.drinkThumb,
            glass = drinkEntity.glass,
            iba = drinkEntity.iba,
            imageAttribution = drinkEntity.imageAttribution,
            imageSource = drinkEntity.imageSource,
            measuredIngredients = drinkEntity.measuredIngredients ?: emptyList(),
            tags = drinkEntity.tags,
            video = drinkEntity.video,
        )
    }

    private fun mapMeasuredIngredients(drinkDto: DrinkDto): List<MeasuredIngredient> {
        return listOfNotNull(
            mapMeasuredIngredient(drinkDto.ingredient1, drinkDto.measure1),
            mapMeasuredIngredient(drinkDto.ingredient2, drinkDto.measure2),
            mapMeasuredIngredient(drinkDto.ingredient3, drinkDto.measure3),
            mapMeasuredIngredient(drinkDto.ingredient4, drinkDto.measure4),
            mapMeasuredIngredient(drinkDto.ingredient5, drinkDto.measure5),
            mapMeasuredIngredient(drinkDto.ingredient6, drinkDto.measure6),
            mapMeasuredIngredient(drinkDto.ingredient7, drinkDto.measure7),
            mapMeasuredIngredient(drinkDto.ingredient8, drinkDto.measure8),
            mapMeasuredIngredient(drinkDto.ingredient9, drinkDto.measure9),
            mapMeasuredIngredient(drinkDto.ingredient10, drinkDto.measure10),
            mapMeasuredIngredient(drinkDto.ingredient11, drinkDto.measure11),
            mapMeasuredIngredient(drinkDto.ingredient12, drinkDto.measure12),
            mapMeasuredIngredient(drinkDto.ingredient13, drinkDto.measure13),
            mapMeasuredIngredient(drinkDto.ingredient14, drinkDto.measure14),
            mapMeasuredIngredient(drinkDto.ingredient15, drinkDto.measure15),
        )
    }

    private fun mapMeasuredIngredient(
        ingredient: String?,
        measure: String?,
    ): MeasuredIngredient? {
        return if (ingredient != null && measure != null) {
            MeasuredIngredient(
                ingredient = ingredient,
                measure = measure,
            )
        } else {
            null
        }
    }

}
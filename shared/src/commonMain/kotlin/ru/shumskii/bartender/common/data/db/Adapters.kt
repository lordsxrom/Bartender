package ru.shumskii.bartender.common.data.db

import app.cash.sqldelight.ColumnAdapter
import ru.shumskii.bartender.common.domain.model.MeasuredIngredient

val measuredIngredientAdapter = object : ColumnAdapter<List<MeasuredIngredient>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(";").map { item ->
                val arrayItem = item.split(",")
                MeasuredIngredient(
                    ingredient = arrayItem[0],
                    measure = arrayItem[0],
                )
            }
        }

    override fun encode(value: List<MeasuredIngredient>): String {
        return value.joinToString(";") { item ->
            "${item.ingredient},${item.measure}"
        }
    }
}

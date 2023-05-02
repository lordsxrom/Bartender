package ru.shumskii.bartender.common.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.shumskii.bartender.common.data.remote.model.DrinksResponse

internal class DrinkService: KtorApi() {

    suspend fun getRandomDrink(): DrinksResponse = client.get {
        pathUrl("random.php")
    }.body()

    suspend fun getDrink(id: Long): DrinksResponse = client.get {
        pathUrl("lookup.php")
        parameter("i", id)
    }.body()

    suspend fun getDrinksByFirstLetter(letter: Char): DrinksResponse = client.get {
        pathUrl("search.php")
        parameter("f", letter)
    }.body()

}
package ru.shumskii.bartender.common.screens.randomize

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.shumskii.bartender.common.AppRes
import ru.shumskii.bartender.common.domain.usecase.GetRandomDrinkUseCase
import ru.shumskii.bartender.common.domain.usecase.IsFavouriteDrinkUseCase
import ru.shumskii.bartender.common.domain.usecase.SetFavouriteDrinkUseCase
import ru.shumskii.bartender.common.screens.drink.DrinkScreenNavObject

class RandomizeViewModel :
    BaseSharedViewModel<RandomizeViewState, RandomizeAction, RandomizeEvent>(
        initialState = RandomizeViewState.Idle
    ),
    KoinComponent {

    private val getRandomDrinkUseCase: GetRandomDrinkUseCase by inject()

    private val isFavouriteDrinkUseCase: IsFavouriteDrinkUseCase by inject()

    private val randomizeDrinkFormatter: RandomizeDrinkFormatter by inject()

    private val setFavouriteDrinkUseCase: SetFavouriteDrinkUseCase by inject()

    override fun obtainEvent(viewEvent: RandomizeEvent) {
        when (viewEvent) {
            is RandomizeEvent.RandomizeClick -> handleRandomizeClick()

            is RandomizeEvent.DrinkClick -> handleDrinkClick(viewEvent.id, viewEvent.drink)

            is RandomizeEvent.FavouriteDrinkClick -> handleFavouriteDrinkClick(viewEvent.id)
        }
    }

    private fun handleRandomizeClick() {
        viewModelScope.launch {
            viewState = RandomizeViewState.Loading
            try {
                val drink = getRandomDrinkUseCase.execute()
                isFavouriteDrinkUseCase.execute(drink.id).collect { isFavourite ->
                    val vo = randomizeDrinkFormatter.format(drink, isFavourite)
                    viewState = RandomizeViewState.Data(vo)
                }
            } catch (t: Throwable) {
                viewState = RandomizeViewState.Error(t.message ?: AppRes.string.sgw_error_message)
            }
        }
    }

    private fun handleDrinkClick(id: Long, drink: String) {
        val navObject = DrinkScreenNavObject(id, drink)
        viewAction = RandomizeAction.OpenDrinkScreen(navObject)
    }

    private fun handleFavouriteDrinkClick(id: Long) {
        viewModelScope.launch {
            setFavouriteDrinkUseCase.execute(id)
        }
    }

}

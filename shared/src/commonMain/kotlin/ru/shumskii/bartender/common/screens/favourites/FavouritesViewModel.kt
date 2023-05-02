package ru.shumskii.bartender.common.screens.favourites

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.shumskii.bartender.common.domain.model.Drink
import ru.shumskii.bartender.common.domain.usecase.GetFavouriteDrinksUseCase
import ru.shumskii.bartender.common.domain.usecase.SetFavouriteDrinkUseCase
import ru.shumskii.bartender.common.screens.catalog.CatalogDrinkFormatter
import ru.shumskii.bartender.common.screens.catalog.CatalogDrinkVo
import ru.shumskii.bartender.common.screens.catalog.CatalogEvent
import ru.shumskii.bartender.common.screens.catalog.CatalogViewState
import ru.shumskii.bartender.common.screens.drink.DrinkScreenNavObject

class FavouritesViewModel :
    BaseSharedViewModel<FavouritesViewState, FavouritesAction, FavouritesEvent>(
        initialState = FavouritesViewState.Idle
    ),
    KoinComponent {

    private val setFavouriteDrinkUseCase: SetFavouriteDrinkUseCase by inject()

    private val getFavouriteDrinksUseCase: GetFavouriteDrinksUseCase by inject()

    private val catalogDrinkFormatter: CatalogDrinkFormatter by inject()

    init {
        viewModelScope.launch {
            getFavouriteDrinksUseCase.execute().collect { drinks ->
                val catalogDrinkVos = drinks.map { drink ->
                    catalogDrinkFormatter.format(
                        drink = drink,
                        isFavourite = true,
                    )
                }
                viewState = FavouritesViewState.Data(catalogDrinkVos)
            }
        }
    }

    override fun obtainEvent(viewEvent: FavouritesEvent) {
        when(viewEvent) {
            is FavouritesEvent.DrinkClick -> {
                val navObject = DrinkScreenNavObject(
                    id = viewEvent.id,
                    drink = viewEvent.drink,
                )
                viewAction = FavouritesAction.OpenDrinkScreen(navObject)
            }

            is FavouritesEvent.SetFavouriteDrink -> {
                viewModelScope.launch {
                    setFavouriteDrinkUseCase.execute(viewEvent.id)
                }
            }

            FavouritesEvent.StartLoading -> {

            }
        }
    }

}

sealed class FavouritesEvent {
    object StartLoading : FavouritesEvent()

    data class DrinkClick(
        val id: Long,
        val drink: String,
    ) : FavouritesEvent()

    data class SetFavouriteDrink(
        val id: Long
    ) : FavouritesEvent()
}

sealed class FavouritesAction {
    data class OpenDrinkScreen(
        val navObject: DrinkScreenNavObject,
    ) : FavouritesAction()
}

sealed class FavouritesViewState {
    object Idle : FavouritesViewState()

    object Loading : FavouritesViewState()

    data class Data(
        val drinks: List<CatalogDrinkVo>,
    ) : FavouritesViewState()

    data class Error(
        val errorMessage: String
    ) : FavouritesViewState()
}

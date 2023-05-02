package ru.shumskii.bartender.common.screens.drink

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.shumskii.bartender.common.domain.usecase.GetDrinkUseCase
import ru.shumskii.bartender.common.domain.usecase.IsFavouriteDrinkUseCase
import ru.shumskii.bartender.common.domain.usecase.SetFavouriteDrinkUseCase


class DrinkViewModel :
    BaseSharedViewModel<DrinkViewState, DrinkAction, DrinkEvent>(
        initialState = DrinkViewState.Idle
    ),
    KoinComponent {

    private val getDrinkUseCase: GetDrinkUseCase by inject()

    private val isFavouriteDrinkUseCase: IsFavouriteDrinkUseCase by inject()

    private val drinkFormatter: DrinkFormatter by inject()

    private val setFavouriteDrinkUseCase: SetFavouriteDrinkUseCase by inject()

    override fun obtainEvent(viewEvent: DrinkEvent) {
        when (viewEvent) {
            is DrinkEvent.StartLoading -> {
                viewModelScope.launch {
                    try {
                        val drink = getDrinkUseCase.execute(viewEvent.id)
                        isFavouriteDrinkUseCase.execute(drink.id).collect { isFavourite ->
                            val vo = drinkFormatter.format(drink, isFavourite)
                            viewState = DrinkViewState.Data(vo)
                        }
                    } catch (t: Throwable) {
                        viewState = DrinkViewState.Error(t.message ?: "Something went wrong")
                    }
                }
            }

            is DrinkEvent.SetFavouriteDrink -> {
                viewModelScope.launch {
                    setFavouriteDrinkUseCase.execute(viewEvent.id)
                }
            }
        }
    }

}

sealed class DrinkEvent {
    data class StartLoading(
        val id: Long
    ) : DrinkEvent()

    data class SetFavouriteDrink(
        val id: Long
    ) : DrinkEvent()
}

sealed class DrinkAction {
// todo
}

sealed class DrinkViewState {
    object Idle : DrinkViewState()

    object Loading : DrinkViewState()

    data class Data(
        val vo: DrinkVo
    ) : DrinkViewState()

    data class Error(
        val errorMessage: String
    ) : DrinkViewState()
}

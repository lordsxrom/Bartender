package ru.shumskii.bartender.common.screens.catalog

import com.adeo.kviewmodel.BaseSharedViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.shumskii.bartender.common.domain.usecase.GetDrinksByFirstLetterUseCase
import ru.shumskii.bartender.common.domain.usecase.GetFavouriteDrinkIdsUseCase
import ru.shumskii.bartender.common.domain.usecase.SetFavouriteDrinkUseCase

class CatalogViewModel :
    BaseSharedViewModel<CatalogViewState, CatalogAction, CatalogEvent>(
        initialState = CatalogViewState.Idle
    ),
    KoinComponent {

    private val getDrinksByFirstLetterUseCase: GetDrinksByFirstLetterUseCase by inject()

    private val setFavouriteDrinkUseCase: SetFavouriteDrinkUseCase by inject()

    private val catalogDrinkFormatter: CatalogDrinkFormatter by inject()

    private val getFavouriteDrinkIdsUseCase: GetFavouriteDrinkIdsUseCase by inject()

    private val array = ArrayDeque(CharRange('a', 'z').toList())

    override fun obtainEvent(viewEvent: CatalogEvent) {
        when (viewEvent) {
            is CatalogEvent.NextBatchLoading -> obtainNextBatchLoading(viewEvent)
            is CatalogEvent.SetFavouriteDrink -> obtainSetFavouriteDrink(viewEvent)
            is CatalogEvent.StartLoading -> obtainStartLoading(viewEvent)
        }
    }

    private fun obtainNextBatchLoading(viewEvent: CatalogEvent.NextBatchLoading) {
        when (viewState) {
            is CatalogViewState.Data -> {
                viewModelScope.launch {
                    try {
                        if (array.isNotEmpty()) {
                            val drinks = getDrinksByFirstLetterUseCase.execute(array.removeFirst())
                            getFavouriteDrinkIdsUseCase.execute().collect { favouriteDrinkIds ->
                                val catalogDrinkVos = drinks.map { drink ->
                                    catalogDrinkFormatter.format(
                                        drink = drink,
                                        isFavourite = favouriteDrinkIds.contains(drink.id),
                                    )
                                }
                                val state = viewState
                                if (state is CatalogViewState.Data) {
                                    viewState =
                                        CatalogViewState.Data(state.drinks + catalogDrinkVos)
                                }
                            }
                        }
                    } catch (t: Throwable) {
                        viewState = CatalogViewState.Error(t.message ?: "Something went wrong")
                    }
                }
            }

            is CatalogViewState.Error,
            is CatalogViewState.Idle,
            is CatalogViewState.Loading -> {
                Napier.e(message = "Illegal ${viewState::class.simpleName} for the ${viewEvent::class.simpleName}")
            }
        }
    }

    private fun obtainSetFavouriteDrink(viewEvent: CatalogEvent.SetFavouriteDrink) {
        when (viewState) {
            is CatalogViewState.Data -> {
                viewModelScope.launch {
                    setFavouriteDrinkUseCase.execute(viewEvent.id)
                }
            }

            is CatalogViewState.Error,
            is CatalogViewState.Idle,
            is CatalogViewState.Loading -> {
                Napier.e(message = "Illegal ${viewState::class.simpleName} for the ${viewEvent::class.simpleName}")
            }
        }
    }

    private fun obtainStartLoading(viewEvent: CatalogEvent.StartLoading) {
        when (viewState) {
            CatalogViewState.Idle -> {
                viewModelScope.launch {
                    try {
                        val drinks =
                            getDrinksByFirstLetterUseCase.execute(array.removeFirst())
                        getFavouriteDrinkIdsUseCase.execute()
                            .collect { favouriteDrinkIds ->
                                val catalogDrinkVos = drinks.map { drink ->
                                    catalogDrinkFormatter.format(
                                        drink = drink,
                                        isFavourite = favouriteDrinkIds.contains(drink.id),
                                    )
                                }
                                viewState = CatalogViewState.Data(catalogDrinkVos)
                            }
                    } catch (t: Throwable) {
                        viewState =
                            CatalogViewState.Error(t.message ?: "Something went wrong")
                    }
                }
            }

            is CatalogViewState.Data,
            is CatalogViewState.Error,
            is CatalogViewState.Loading -> {
                Napier.e(message = "Illegal ${viewState::class.simpleName} for the ${viewEvent::class.simpleName}")
            }
        }
    }

}

sealed class CatalogEvent {
    object StartLoading : CatalogEvent()

    object NextBatchLoading : CatalogEvent()

    data class SetFavouriteDrink(
        val id: Long
    ) : CatalogEvent()
}

sealed class CatalogAction {
// todo
}

sealed class CatalogViewState {
    object Idle : CatalogViewState()

    object Loading : CatalogViewState()

    data class Data(
        val drinks: List<CatalogDrinkVo>
    ) : CatalogViewState()

    data class Error(
        val errorMessage: String
    ) : CatalogViewState()
}

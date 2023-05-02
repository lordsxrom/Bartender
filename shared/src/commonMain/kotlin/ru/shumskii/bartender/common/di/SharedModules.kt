package ru.shumskii.bartender.common.di

import Database
import data.DrinkEntity
import org.koin.dsl.module
import ru.shumskii.bartender.common.data.db.DriverFactory
import ru.shumskii.bartender.common.data.db.measuredIngredientAdapter
import ru.shumskii.bartender.common.data.mapper.DrinkMapper
import ru.shumskii.bartender.common.data.prefs.KMMPreference
import ru.shumskii.bartender.common.data.remote.DrinkRemoteDataSource
import ru.shumskii.bartender.common.data.remote.DrinkService
import ru.shumskii.bartender.common.data.repository.DrinkRepositoryImpl
import ru.shumskii.bartender.common.data.repository.FavouriteRepositoryImpl
import ru.shumskii.bartender.common.data.repository.SettingsRepositoryImpl
import ru.shumskii.bartender.common.domain.repository.DrinkRepository
import ru.shumskii.bartender.common.domain.repository.FavouriteRepository
import ru.shumskii.bartender.common.domain.repository.SettingsRepository
import ru.shumskii.bartender.common.domain.usecase.GetDrinkUseCase
import ru.shumskii.bartender.common.domain.usecase.GetDrinksByFirstLetterUseCase
import ru.shumskii.bartender.common.domain.usecase.GetFavouriteDrinkIdsUseCase
import ru.shumskii.bartender.common.domain.usecase.GetFavouriteDrinksUseCase
import ru.shumskii.bartender.common.domain.usecase.GetRandomDrinkUseCase
import ru.shumskii.bartender.common.domain.usecase.IsDarkModeEnabledUseCase
import ru.shumskii.bartender.common.domain.usecase.IsFavouriteDrinkUseCase
import ru.shumskii.bartender.common.domain.usecase.SetDarkModeEnabledUseCase
import ru.shumskii.bartender.common.domain.usecase.SetFavouriteDrinkUseCase
import ru.shumskii.bartender.common.screens.catalog.CatalogDrinkFormatter
import ru.shumskii.bartender.common.screens.drink.DrinkFormatter
import ru.shumskii.bartender.common.screens.randomize.RandomizeDrinkFormatter
import ru.shumskii.bartender.common.util.provideDispatcher

private fun dataModule(platformConfiguration: PlatformConfiguration) = module {
    factory { DrinkRemoteDataSource(get(), get()) }
    factory { DrinkService() }
    factory { DrinkMapper() }
    single {
        DriverFactory(platformConfiguration)
            .createDriver("bartender.db")
    }
    single {
        Database(
            get(),
            DrinkEntity.Adapter(measuredIngredientAdapter)
        )
    }
    single { KMMPreference(platformConfiguration.kmmContext) }
    factory<DrinkRepository> { DrinkRepositoryImpl(get(), get(), get()) }
    factory<FavouriteRepository> { FavouriteRepositoryImpl(get(), get()) }
    factory<SettingsRepository> { SettingsRepositoryImpl(get()) }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    factory { GetRandomDrinkUseCase(get()) }
    factory { GetDrinkUseCase(get()) }
    factory { GetDrinksByFirstLetterUseCase(get()) }
    factory { SetFavouriteDrinkUseCase(get()) }
    factory { GetFavouriteDrinkIdsUseCase(get()) }
    factory { GetFavouriteDrinksUseCase(get(), get()) }
    factory { IsFavouriteDrinkUseCase(get()) }
    factory { IsDarkModeEnabledUseCase(get()) }
    factory { SetDarkModeEnabledUseCase(get()) }
}

private val featureModule = module {
    factory { CatalogDrinkFormatter() }
    factory { DrinkFormatter() }
    factory { RandomizeDrinkFormatter() }
}

private fun sharedModules(platformConfiguration: PlatformConfiguration) = listOf(
    featureModule,
    domainModule,
    dataModule(platformConfiguration),
    utilityModule,
)

fun getSharedModules(platformConfiguration: PlatformConfiguration) =
    sharedModules(platformConfiguration)
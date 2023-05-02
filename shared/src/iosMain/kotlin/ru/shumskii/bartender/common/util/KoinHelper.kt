package ru.shumskii.bartender.common.util

import org.koin.core.context.startKoin
import ru.shumskii.bartender.common.di.PlatformConfiguration
import ru.shumskii.bartender.common.di.getSharedModules

fun initKoin(platformConfiguration: PlatformConfiguration){
    startKoin {
        modules(getSharedModules(platformConfiguration))
    }
}
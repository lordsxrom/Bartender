package ru.shumskii.bartender.common.di

import ru.shumskii.bartender.common.data.prefs.KMMContext

enum class Platform {
    Android, iOS,
}

expect class PlatformConfiguration {
    val appName: String
    val platform: Platform
    val kmmContext: KMMContext
}

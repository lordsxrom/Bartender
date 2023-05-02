package ru.shumskii.bartender.common.di

import platform.darwin.NSObject
import ru.shumskii.bartender.common.data.prefs.KMMContext

actual class PlatformConfiguration {
    actual val appName: String
        get() = "Bartender"

    actual val platform: Platform
        get() = Platform.iOS

    actual val kmmContext: KMMContext
        get() = NSObject()
}
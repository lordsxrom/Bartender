package ru.shumskii.bartender.common.di

import android.app.Application
import ru.shumskii.bartender.common.data.prefs.KMMContext

actual class PlatformConfiguration constructor(
    val application: Application,
    actual val appName: String,
) {

    actual val platform: Platform
        get() = Platform.Android

    actual val kmmContext: KMMContext
        get() = application

}
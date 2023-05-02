package ru.shumskii.bartender

import android.app.Application
import org.koin.core.context.startKoin
import ru.shumskii.bartender.common.di.PlatformConfiguration
import ru.shumskii.bartender.common.di.getSharedModules
import ru.shumskii.bartender.di.appModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            val platformConfiguration = PlatformConfiguration(
                application = this@App,
                appName = "Bartender"
            )
            modules(appModule + getSharedModules(platformConfiguration))
        }
    }

}
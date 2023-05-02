package ru.shumskii.bartender.common.data.db

import app.cash.sqldelight.db.SqlDriver
import ru.shumskii.bartender.common.di.PlatformConfiguration

expect class DriverFactory(platformConfiguration: PlatformConfiguration) {
    fun createDriver(name: String): SqlDriver
}
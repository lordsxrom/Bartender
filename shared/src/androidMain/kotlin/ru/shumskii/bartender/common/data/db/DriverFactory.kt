package ru.shumskii.bartender.common.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ru.shumskii.bartender.common.di.PlatformConfiguration

actual class DriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {
    actual fun createDriver(name: String): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, platformConfiguration.application, name)
    }
}
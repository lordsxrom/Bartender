package ru.shumskii.bartender.common.data.db

import Database
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ru.shumskii.bartender.common.di.PlatformConfiguration

actual class DriverFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration,
) {

    actual fun createDriver(name: String): SqlDriver {
        return NativeSqliteDriver(Database.Schema, name)
    }

}
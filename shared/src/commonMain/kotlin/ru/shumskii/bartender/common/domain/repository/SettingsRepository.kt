package ru.shumskii.bartender.common.domain.repository

interface SettingsRepository {

    fun isDarkModeEnabled(): Boolean

    fun setDarkModeEnabled(isEnable: Boolean)

}
package ru.shumskii.bartender.common.data.repository

import ru.shumskii.bartender.common.data.prefs.KMMPreference
import ru.shumskii.bartender.common.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val kmmPreference: KMMPreference,
) : SettingsRepository {

    override fun isDarkModeEnabled(): Boolean {
        return kmmPreference.getBool(DARK_MODE_ENABLED, false)
    }

    override fun setDarkModeEnabled(isEnable: Boolean) {
        kmmPreference.put(DARK_MODE_ENABLED, isEnable)
    }

    companion object {
        private const val DARK_MODE_ENABLED = "dark_mode_enabled"
    }
}
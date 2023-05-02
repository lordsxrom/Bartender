package ru.shumskii.bartender.common.domain.usecase

import ru.shumskii.bartender.common.domain.repository.SettingsRepository

class IsDarkModeEnabledUseCase constructor(
    private val settingsRepository: SettingsRepository,
) {

    fun execute(): Boolean {
        return settingsRepository.isDarkModeEnabled()
    }

}
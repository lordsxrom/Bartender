package ru.shumskii.bartender.common.domain.usecase

import ru.shumskii.bartender.common.domain.repository.SettingsRepository

class SetDarkModeEnabledUseCase constructor(
    private val settingsRepository: SettingsRepository,
) {

    fun execute(isEnable: Boolean) {
        settingsRepository.setDarkModeEnabled(isEnable)
    }

}
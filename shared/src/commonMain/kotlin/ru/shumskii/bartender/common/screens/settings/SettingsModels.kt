package ru.shumskii.bartender.common.screens.settings

sealed class SettingsEvent {
    object StartLoading : SettingsEvent()

    data class SetDarkModeEnabled(
        val isEnabled: Boolean,
    ) : SettingsEvent()

}

sealed class SettingsAction {
    class ShowReloadDialog : SettingsAction()
}

sealed class SettingsViewState {

    object Idle : SettingsViewState()

    object Loading : SettingsViewState()

    data class Data(
        val vo: SettingsVo
    ) : SettingsViewState()

    data class Error(
        val errorMessage: String
    ) : SettingsViewState()
}
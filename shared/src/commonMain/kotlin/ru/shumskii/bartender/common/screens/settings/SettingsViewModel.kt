package ru.shumskii.bartender.common.screens.settings

import com.adeo.kviewmodel.BaseSharedViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.shumskii.bartender.common.domain.usecase.IsDarkModeEnabledUseCase
import ru.shumskii.bartender.common.domain.usecase.SetDarkModeEnabledUseCase

class SettingsViewModel :
    BaseSharedViewModel<SettingsViewState, SettingsAction, SettingsEvent>(
        initialState = SettingsViewState.Idle
    ),
    KoinComponent {

    private val isDarkModeEnabledUseCase: IsDarkModeEnabledUseCase by inject()

    private val setDarkModeEnabledUseCase: SetDarkModeEnabledUseCase by inject()

    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.StartLoading -> handleStartLoading()
            is SettingsEvent.SetDarkModeEnabled -> handleSetDarkModeEnabled(viewEvent.isEnabled)
        }
    }

    private fun handleSetDarkModeEnabled(isEnabled: Boolean) {
        setDarkModeEnabledUseCase.execute(isEnabled)
        viewState = SettingsViewState.Data(
            vo = SettingsVo(
                isDarkModeEnabled = isEnabled
            )
        )
        viewAction = SettingsAction.ShowReloadDialog()
    }

    private fun handleStartLoading() {
        viewState = SettingsViewState.Data(
            vo = SettingsVo(
                isDarkModeEnabled = isDarkModeEnabledUseCase.execute()
            )
        )
    }

}

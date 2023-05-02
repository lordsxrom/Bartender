package ru.shumskii.bartender.common/*
 * Copyright 2020-2021 JetBrains s.r.o. and respective authors and developers.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.shumskii.bartender.common.navigation.navigationGraph
import ru.shumskii.bartender.common.ui.BartenderTheme

fun MainViewController(): UIViewController = ComposeUIViewController {
    BartenderTheme {
        val odysseyConfiguration = OdysseyConfiguration()

        setNavigationContent(odysseyConfiguration) {
            navigationGraph()
        }
    }
}
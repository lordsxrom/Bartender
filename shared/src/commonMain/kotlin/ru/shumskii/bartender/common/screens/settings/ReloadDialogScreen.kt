package ru.shumskii.bartender.common.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReloadDialogScreen(onCloseClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(Odyssey.color.primaryBackground),
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Need reload device",
            fontSize = 24.sp,
//            color = Odyssey.color.primaryText,
        )
        Button(
            onClick = onCloseClick,
        ) {
            Text("Ok")
        }
    }
}
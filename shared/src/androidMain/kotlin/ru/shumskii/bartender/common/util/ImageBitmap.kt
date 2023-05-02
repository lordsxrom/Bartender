package ru.shumskii.bartender.common.util

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun ByteArray.toImageBitmap(): ImageBitmap {
    return BitmapFactory
        .decodeByteArray(this, 0, this.size)
        .asImageBitmap()
}
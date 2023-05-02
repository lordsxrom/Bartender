package ru.shumskii.bartender.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.core.use
import ru.shumskii.bartender.common.util.toImageBitmap

@Composable
internal fun AsyncImage(
    url: String,
    contentDescription: String? = "",
    modifier: Modifier = Modifier
) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    image?.let {
        Image(
            modifier = modifier,
            bitmap = it,
            contentDescription = contentDescription
        )
    } ?: run {
        Spacer(modifier = modifier)
    }
    LaunchedEffect(key1 = url, block = {
        val bytes: ByteArray = HttpClient().use { client ->
            client.get(url).body()
        }
        image = bytes.toImageBitmap()
    })
}
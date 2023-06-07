package ru.shumskii.bartender.common.screens.randomize

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Preview(name = "test")
@Composable
fun RandomizeScreenDataPreview() {
    RandomizeScreenData(
        state = RandomizeViewState.Data(
            vo = RandomizeDrinkVo(
                id = 111L,
                drink = "Test drink",
                drinkThumb = "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcSUH6UD3dF7S3Wl9Eecuzu5m3mral-3NXZsJgzcerWFDJDzkZII1D4fXCPdUXucG_wWqr4AtYVo6mwbEEY17F4",
                alcoholic = null,
                isFavourite = true
            )
        ),
        onFavouriteDrinkClicked = {},
        onRandomizeButtonClicked = {},
        onDrinkClicked = {},
    )
}
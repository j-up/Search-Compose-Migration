package com.kakao.search.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

object Search {
    val LocalColors = staticCompositionLocalOf { lightSearchColors }
    val LocalTypography = staticCompositionLocalOf { SearchTypography(lightSearchColors) }

    @Composable
    fun SearchTheme(
        isDarkMode: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colors = if (isDarkMode) darkSearchColors else lightSearchColors
        val typography = SearchTypography(colors)

        CompositionLocalProvider(
            LocalColors provides colors,
            LocalTypography provides typography,
            content = content
        )
    }
}

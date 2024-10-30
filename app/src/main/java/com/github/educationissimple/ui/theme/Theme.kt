package com.github.educationissimple.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.locals.Dimensions
import com.github.educationissimple.presentation.locals.LocalSpacing


private val LightColorScheme = lightColorScheme(
    primary = Highlight.Dark,
    secondary = Highlight.Medium,
    tertiary = Highlight.Light,
    background = Neutral.Light.Lightest,
    onPrimary = Neutral.Light.Lightest,
    onSecondary = Neutral.Light.Lightest,
    onTertiary = Neutral.Light.Lightest,
//    surface = Neutral.Light.Lightest,

    /* Other default colors to override
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EducationIsSimpleTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}
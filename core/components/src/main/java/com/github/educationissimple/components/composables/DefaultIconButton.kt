package com.github.educationissimple.components.composables

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral

@Composable
fun DefaultIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors? = null,
    content: @Composable () -> Unit
) = IconButton(
    onClick = onClick,
    colors = colors ?: IconButtonDefaults.iconButtonColors(
        contentColor = Neutral.Light.Light,
        containerColor = Highlight.Darkest,
        disabledContentColor = Neutral.Light.Light,
        disabledContainerColor = Highlight.Medium
    ),
    enabled = enabled,
    modifier = modifier
) {
    content()
}
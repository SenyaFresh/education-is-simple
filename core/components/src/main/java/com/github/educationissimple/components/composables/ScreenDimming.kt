package com.github.educationissimple.components.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.educationissimple.components.colors.Support

@Composable
fun ScreenDimming(onClick: () -> Unit) = Surface(
    modifier = Modifier
        .fillMaxSize()
        .clickable { onClick() },
    color = Support.Dimming.Dark
) {

}
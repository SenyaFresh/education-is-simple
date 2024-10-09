package com.github.components.colors

import androidx.compose.ui.graphics.Color

sealed class Support {

    data object Success: Support() {

        val Dark = Color(0xFF298267)

        val Medium = Color(0xFF3AC0A0)

        val Light = Color(0xFFE7F4E8)

    }

    data object Warning: Support() {

        val Dark = Color(0xFFE86339)

        val Medium = Color(0xFFFFB37C)

        val Light = Color(0xFFFFF4E4)

    }

    data object Error: Support() {

        val Dark = Color(0xFFED3241)

        val Medium = Color(0xFFFF616D)

        val Light = Color(0xFFFFE2E5)

    }
}
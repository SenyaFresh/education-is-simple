package com.github.components.colors

import androidx.compose.ui.graphics.Color

sealed class Neutral {

    data object Light: Neutral() {

        val Darkest = Color(0xFFC5C6CC)

        val Dark = Color(0xFFD4D6DD)

        val Medium = Color(0xFFE8E9F1)

        val Light = Color(0xFFF8F9FE)

        val Lightest = Color(0xFFFFFFFF)

    }

    data object Dark: Neutral() {

        val Darkest = Color(0xFF1E1E1E)

        val Dark = Color(0xFF2F3036)

        val Medium = Color(0xFF494A50)

        val Light = Color(0xFF71727A)

        val Lightest = Color(0xFF8F9098)

    }
}
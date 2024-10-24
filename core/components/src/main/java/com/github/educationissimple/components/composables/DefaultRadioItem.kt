package com.github.educationissimple.components.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultRadioItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    textColor: Color,
    selectedColor: Color,
    unselectedColor: Color,
    modifier: Modifier = Modifier
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.clickable { onClick() }
) {
    RadioButton(
        selected = selected,
        colors = RadioButtonDefaults.colors(
            selectedColor = selectedColor,
            unselectedColor = unselectedColor
        ),
        onClick = { onClick() }
    )
    Text(text = text, color = textColor)
}
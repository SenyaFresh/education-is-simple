package com.github.educationissimple.components.composables

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.educationissimple.components.colors.Neutral

@Composable
fun DefaultTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null
) = OutlinedTextField(
    value = text,
    onValueChange = onValueChange,
    colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Neutral.Light.Light,
        focusedBorderColor = Color.Transparent,
        unfocusedContainerColor = Neutral.Light.Light,
        unfocusedBorderColor = Color.Transparent,
    ),
    modifier = modifier,
    label = label,
    trailingIcon = trailingIcon
)
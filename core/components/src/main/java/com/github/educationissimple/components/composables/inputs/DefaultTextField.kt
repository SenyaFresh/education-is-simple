package com.github.educationissimple.components.composables.inputs

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.github.educationissimple.components.colors.Neutral

@Composable
fun DefaultTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Neutral.Light.Light,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) = OutlinedTextField(
    value = text,
    onValueChange = onValueChange,
    colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = containerColor,
        focusedBorderColor = Color.Transparent,
        unfocusedContainerColor = containerColor,
        unfocusedBorderColor = Color.Transparent,
    ),
    textStyle = textStyle,
    placeholder = placeholder,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
    modifier = modifier
)
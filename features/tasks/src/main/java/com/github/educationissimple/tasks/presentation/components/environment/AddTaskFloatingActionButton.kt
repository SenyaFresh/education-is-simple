package com.github.educationissimple.tasks.presentation.components.environment

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral

@Composable
fun AddTaskFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = FloatingActionButton(
    onClick = onClick,
    containerColor = Highlight.Darkest,
    contentColor = Neutral.Light.Lightest,
    modifier = modifier
) {
    Icon(imageVector = Icons.Default.Add, contentDescription = null)
}
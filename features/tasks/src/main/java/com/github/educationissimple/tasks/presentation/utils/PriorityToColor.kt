package com.github.educationissimple.tasks.presentation.utils

import androidx.compose.ui.graphics.Color
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.colors.Support
import com.github.educationissimple.tasks.domain.entities.Task

fun Task.Priority.toColor(): Color = when (this) {
    Task.Priority.TopPriority -> Support.Warning.Dark
    Task.Priority.SecondaryPriority -> Support.Warning.Medium
    Task.Priority.NoPriority -> Neutral.Dark.Lightest
}
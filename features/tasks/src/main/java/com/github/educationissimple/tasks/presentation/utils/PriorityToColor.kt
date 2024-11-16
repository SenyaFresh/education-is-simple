package com.github.educationissimple.tasks.presentation.utils

import androidx.compose.ui.graphics.Color
import com.github.educationissimple.tasks.domain.entities.Task

fun Task.Priority.toColor(): Color = when (this) {
    Task.Priority.TopPriority -> TopPriorityColor
    Task.Priority.SecondaryPriority -> SecondaryPriorityColor
    Task.Priority.NoPriority -> NoPriorityColor
}

val TopPriorityColor = Color(0xFFE86339)
val SecondaryPriorityColor = Color(0xFFFFB37C)
val NoPriorityColor = Color(0xFF8F9098)

package com.github.educationissimple.tasks.presentation.utils

import androidx.compose.ui.graphics.Color
import com.github.educationissimple.tasks.domain.entities.Task

/**
 * Converts a [Task.Priority] enum value to a corresponding [Color] based on the task's priority.
 *
 * This extension function maps the [Task.Priority] values to specific colors:
 * - [Task.Priority.TopPriority] is mapped to a color representing the highest priority.
 * - [Task.Priority.SecondaryPriority] is mapped to a color for secondary priorities.
 * - [Task.Priority.NoPriority] is mapped to a neutral color for tasks without a priority.
 *
 * @return The color corresponding to the task's priority.
 */
fun Task.Priority.toColor(): Color = when (this) {
    Task.Priority.TopPriority -> TopPriorityColor
    Task.Priority.SecondaryPriority -> SecondaryPriorityColor
    Task.Priority.NoPriority -> NoPriorityColor
}

val TopPriorityColor = Color(0xFFE86339)
val SecondaryPriorityColor = Color(0xFFFFB37C)
val NoPriorityColor = Color(0xFF8F9098)

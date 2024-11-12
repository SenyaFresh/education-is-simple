package com.github.educationissimple.tasks.domain.entities

import java.time.LocalDate

typealias TaskId = Long

data class Task(
    val id: TaskId = 0,
    val text: String,
    val isCompleted: Boolean = false,
    val categoryId: TaskCategoryId? = null,
    val priority: Priority = Priority.NoPriority,
    val date: LocalDate? = null,
    val description: String? = null
) {

    sealed class Priority(val value: Int) {

        data object TopPriority : Priority(2)

        data object SecondaryPriority : Priority(1)

        data object NoPriority : Priority(0)

        companion object {
            fun fromValue(value: Int): Priority {
                return when (value) {
                    2 -> TopPriority
                    1 -> SecondaryPriority
                    else -> NoPriority
                }
            }
        }

    }
}
package com.github.educationissimple.tasks.domain.entities

import com.github.educationissimple.tasks.domain.entities.Task.Priority
import java.time.LocalDate

typealias TaskId = Long

/**
 * A data class representing a task with various attributes.
 *
 * @property id Unique identifier for the task.
 * @property text The title or main text of the task.
 * @property isCompleted Whether the task has been completed.
 * @property categoryId Optional identifier for the category to which the task belongs.
 * @property priority The priority level of the task, default is [Priority.NoPriority].
 * @property date Optional due date for the task.
 * @property description Optional detailed description of the task.
 */
data class Task(
    val id: TaskId = 0,
    val text: String,
    val isCompleted: Boolean = false,
    val categoryId: TaskCategoryId? = null,
    val priority: Priority = Priority.NoPriority,
    val date: LocalDate? = null,
    val description: String? = null
) {

    /**
     * Represents the priority levels for a task.
     *
     * @property value The numerical representation of the priority level.
     */
    sealed class Priority(val value: Int) {

        data object TopPriority : Priority(2)

        data object SecondaryPriority : Priority(1)

        data object NoPriority : Priority(0)

        companion object {
            /**
             * Returns a [Priority] based on its numerical representation.
             *
             * @param value The numerical representation of a priority level.
             * @return The corresponding [Priority] instance.
             */
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
package com.github.educationissimple.tasks.domain.entities

typealias TaskCategoryId = Long

/**
 * A data class representing a category for tasks.
 *
 * @property id The unique identifier for the category.
 * @property name The name of the category.
 */
data class TaskCategory(
    val id: TaskCategoryId,
    val name: String
) {
    companion object {
        const val NO_CATEGORY_ID = -1L
    }
}
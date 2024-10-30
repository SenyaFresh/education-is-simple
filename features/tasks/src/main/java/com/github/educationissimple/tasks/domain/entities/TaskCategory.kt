package com.github.educationissimple.tasks.domain.entities

typealias TaskCategoryId = Long

data class TaskCategory(
    val id: TaskCategoryId,
    val name: String
) {
    companion object {
        const val NO_CATEGORY_ID = -1L
    }
}
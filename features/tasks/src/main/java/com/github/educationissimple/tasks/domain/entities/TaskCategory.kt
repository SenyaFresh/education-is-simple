package com.github.educationissimple.tasks.domain.entities

data class TaskCategory(
    val id: Long,
    val name: String
) {
    companion object {
        const val NO_CATEGORY_ID = -1L
    }
}
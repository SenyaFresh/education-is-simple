package com.github.educationissimple.tasks.domain.entities


data class Task(
    val id: Long = 0,
    val text: String,
    val isCompleted: Boolean = false,
    val categoryId: Long? = null,
    val date: String? = null
)
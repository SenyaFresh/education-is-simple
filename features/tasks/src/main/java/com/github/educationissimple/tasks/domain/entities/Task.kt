package com.github.educationissimple.tasks.domain.entities


data class Task(val id: Int = 0, val text: String, val isCompleted: Boolean = false, val date: String? = null)
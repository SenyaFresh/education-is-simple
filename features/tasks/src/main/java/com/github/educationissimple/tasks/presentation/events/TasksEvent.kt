package com.github.educationissimple.tasks.presentation.events

import com.github.educationissimple.tasks.domain.entities.Task

sealed class TasksEvent {

    data class CompleteTask(val taskId: Long): TasksEvent()

    data class CancelTaskCompletion(val taskId: Long): TasksEvent()

    data class ChangeTaskPriority(val taskId: Long, val priority: Task.Priority): TasksEvent()

    data class AddTask(val task: Task): TasksEvent()

    data class DeleteTask(val taskId: Long): TasksEvent()

    data class ChangeCategory(val categoryId: Long?): TasksEvent()

    data class AddCategory(val name: String): TasksEvent()

    data class DeleteCategory(val categoryId: Long): TasksEvent()

}
package com.github.educationissimple.tasks.presentation.events

import com.github.educationissimple.tasks.domain.entities.Task

sealed class TasksEvent {

    data class CompleteTask(val taskId: Long): TasksEvent()

    data class CancelTaskCompletion(val taskId: Long): TasksEvent()

    data class AddTask(val task: Task): TasksEvent()

    data class DeleteTask(val taskId: Long): TasksEvent()

}
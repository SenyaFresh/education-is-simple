package com.github.educationissimple.tasks.presentation.events

import com.github.educationissimple.tasks.domain.entities.Task

sealed class TasksEvent {

    data class CompleteTask(val task: Task): TasksEvent()

    data class CancelTaskCompletion(val task: Task): TasksEvent()

    data class AddTask(val task: Task): TasksEvent()

    data class DeleteTask(val task: Task): TasksEvent()

}
package com.github.educationissimple.tasks.presentation.events

import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import java.time.LocalDate

sealed class TasksEvent {

    data class ChangeTaskSearchText(val text: String): TasksEvent()

    data class ChangeTasksSelectionDate(val date: LocalDate): TasksEvent()

    data class UpdateTask(val updatedTask: Task): TasksEvent()

    data class ChangeSortType(val sortType: SortType): TasksEvent()

    data class AddTask(val task: Task): TasksEvent()

    data class DeleteTask(val taskId: Long): TasksEvent()

    data class ChangeCategory(val categoryId: Long?): TasksEvent()

    data class AddCategory(val name: String): TasksEvent()

    data class DeleteCategory(val categoryId: Long): TasksEvent()

}
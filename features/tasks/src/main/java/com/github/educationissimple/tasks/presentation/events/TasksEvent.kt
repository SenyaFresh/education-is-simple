package com.github.educationissimple.tasks.presentation.events

import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import java.time.LocalDate

/**
 * Represents different events that can occur in the tasks feature of the application.
 *
 */
sealed class TasksEvent {

    /**
     * Event to change the search text for filtering tasks.
     *
     * @param text The new search text to filter tasks.
     */
    data class ChangeTaskSearchText(val text: String) : TasksEvent()

    /**
     * Event to change the selected date for filtering tasks.
     *
     * @param date The selected date to filter tasks by.
     */
    data class ChangeTasksSelectionDate(val date: LocalDate) : TasksEvent()

    /**
     * Event to update an existing task with new information.
     *
     * @param updatedTask The task with updated data.
     */
    data class UpdateTask(val updatedTask: Task) : TasksEvent()

    /**
     * Event to change the sorting type for the task list.
     *
     * @param sortType The new sorting type to be applied.
     */
    data class ChangeSortType(val sortType: SortType) : TasksEvent()

    /**
     * Event to add a new task to the list.
     *
     * @param task The task to be added.
     */
    data class AddTask(val task: Task) : TasksEvent()

    /**
     * Event to delete a task from the list.
     *
     * @param taskId The ID of the task to be deleted.
     */
    data class DeleteTask(val taskId: Long) : TasksEvent()

    /**
     * Event to change the category of the task list.
     *
     * @param categoryId The ID of the category to filter tasks by. Null means no category filter.
     */
    data class ChangeCategory(val categoryId: Long?) : TasksEvent()

    /**
     * Event to add a new category to the system.
     *
     * @param name The name of the new category to be added.
     */
    data class AddCategory(val name: String) : TasksEvent()

    /**
     * Event to delete a category by its ID.
     *
     * @param categoryId The ID of the category to be deleted.
     */
    data class DeleteCategory(val categoryId: Long) : TasksEvent()

    /**
     * Event to add a new reminder for a task.
     *
     * @param taskReminder The reminder to be added.
     */
    data class AddTaskReminder(val taskReminder: TaskReminder) : TasksEvent()

    /**
     * Event to delete a reminder from a task.
     *
     * @param taskReminder The reminder to be deleted.
     */
    data class DeleteTaskReminder(val taskReminder: TaskReminder) : TasksEvent()

    /**
     * Event to reload the list of tasks.
     */
    data object ReloadTasks : TasksEvent()

    /**
     * Event to reload the list of categories.
     */
    data object ReloadCategories : TasksEvent()

    /**
     * Event to reload the list of reminders.
     */
    data object ReloadReminders : TasksEvent()

}
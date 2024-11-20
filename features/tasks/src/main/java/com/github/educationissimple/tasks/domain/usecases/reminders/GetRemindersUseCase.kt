package com.github.educationissimple.tasks.domain.usecases.reminders

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting reminders from the tasks repository.
 */
class GetRemindersUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Fetches all reminders associated with a specific task.
     * @param taskId The ID of the task whose reminders are to be fetched.
     * @return A [Flow] emitting a [ResultContainer] containing a list of task reminders.
     */
    suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<TaskReminder>>> {
        return tasksRepository.getRemindersForTask(taskId)
    }

    /**
     * Reloads all reminders from the data source.
     */
    suspend fun reloadReminders() {
        tasksRepository.reloadReminders()
    }

    /**
     * Fetches all reminders.
     * @return A [Flow] emitting a [ResultContainer] containing a list of all task reminders.
     */
    suspend fun getAllReminders(): Flow<ResultContainer<List<TaskReminder>>> {
        return tasksRepository.getAllReminders()
    }

}
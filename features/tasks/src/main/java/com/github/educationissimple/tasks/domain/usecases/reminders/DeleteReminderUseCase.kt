package com.github.educationissimple.tasks.domain.usecases.reminders

import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

/**
 * Use case for deleting an existing task reminder.
 */
class DeleteReminderUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Deletes an existing task reminder.
     * @param reminder The task reminder to be deleted.
     */
    suspend fun deleteReminder(reminder: TaskReminder) {
        tasksRepository.deleteReminder(reminder)
    }

}
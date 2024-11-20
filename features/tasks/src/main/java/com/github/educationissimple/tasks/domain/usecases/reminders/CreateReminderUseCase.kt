package com.github.educationissimple.tasks.domain.usecases.reminders

import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

/**
 * Use case for creating a new task reminder.
 */
class CreateReminderUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Creates a new task reminder.
     * @param reminder The task reminder to be created.
     */
    suspend fun createReminder(reminder: TaskReminder) {
        tasksRepository.createReminder(reminder)
    }

}
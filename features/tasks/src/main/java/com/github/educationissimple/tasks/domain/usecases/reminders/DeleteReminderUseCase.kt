package com.github.educationissimple.tasks.domain.usecases.reminders

import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun deleteReminder(reminder: TaskReminder) {
        tasksRepository.deleteReminder(reminder)
    }

}
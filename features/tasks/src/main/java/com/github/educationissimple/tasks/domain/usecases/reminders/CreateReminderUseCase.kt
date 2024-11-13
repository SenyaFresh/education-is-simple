package com.github.educationissimple.tasks.domain.usecases.reminders

import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class CreateReminderUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun createReminder(reminder: TaskReminder) {
        tasksRepository.createReminder(reminder)
    }

}
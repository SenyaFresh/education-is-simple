package com.github.educationissimple.tasks.domain.usecases.reminders

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<TaskReminder>>> {
        return tasksRepository.getRemindersForTask(taskId)
    }

    suspend fun getAllReminders(): Flow<ResultContainer<List<TaskReminder>>> {
        return tasksRepository.getAllReminders()
    }

}
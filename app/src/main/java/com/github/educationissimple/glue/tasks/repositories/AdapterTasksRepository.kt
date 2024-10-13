package com.github.educationissimple.glue.tasks.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.repositories.TasksDataRepository
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.glue.tasks.mappers.mapToTask
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdapterTasksRepository @Inject constructor(
    private val tasksDataRepository: TasksDataRepository
) : TasksRepository {
    override suspend fun getPreviousTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getPreviousTasks().mapToTask()
    }

    override suspend fun getTodayTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getTodayTasks().mapToTask()
    }

    override suspend fun getFutureTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getFutureTasks().mapToTask()
    }

    override suspend fun getCompletedTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getCompletedTasks().mapToTask()
    }

    override suspend fun completeTask(task: Task) {
        tasksDataRepository.completeTask(task.id)
    }

    override suspend fun cancelTask(task: Task) {
        tasksDataRepository.cancelTaskCompletion(task.id)
    }

    override suspend fun addTask(task: Task) {
        tasksDataRepository.addTask(NewTaskTuple(task.id, task.text))
    }

    override suspend fun deleteTask(task: Task) {
        tasksDataRepository.deleteTask(task.id)
    }


}
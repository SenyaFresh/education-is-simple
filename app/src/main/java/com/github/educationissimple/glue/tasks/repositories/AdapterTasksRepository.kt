package com.github.educationissimple.glue.tasks.repositories

import android.database.sqlite.SQLiteConstraintException
import com.github.educationissimple.R
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.UserFriendlyException
import com.github.educationissimple.data.tasks.repositories.TasksDataRepository
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.glue.tasks.mappers.mapToTask
import com.github.educationissimple.glue.tasks.mappers.mapToTaskCategory
import com.github.educationissimple.glue.tasks.mappers.mapToTaskDataEntity
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
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

    override suspend fun completeTask(taskId: Long) {
        tasksDataRepository.completeTask(taskId)
    }

    override suspend fun cancelTask(taskId: Long) {
        tasksDataRepository.cancelTaskCompletion(taskId)
    }

    override suspend fun addTask(task: Task) {
        tasksDataRepository.addTask(
            NewTaskTuple(
                id = task.id,
                text = task.text,
                categoryId = task.categoryId
            )
        )
    }

    override suspend fun updateTask(task: Task) {
        tasksDataRepository.updateTask(task.mapToTaskDataEntity())
    }

    override suspend fun deleteTask(taskId: Long) {
        tasksDataRepository.deleteTask(taskId)
    }

    override suspend fun getCategories(): Flow<ResultContainer<List<TaskCategory>>> {
        return tasksDataRepository.getCategories().mapToTaskCategory()
    }

    override suspend fun createCategory(name: String) {
        try {
            tasksDataRepository.createCategory(
                NewTaskCategoryTuple(
                    name.lowercase().trim()
                )
            )
        } catch (exception: SQLiteConstraintException) {
            throw UserFriendlyException(
                userFriendlyMessage = Core.resources.getString(R.string.category_constraint_exception),
                cause = exception
            )
        }
    }

    override suspend fun deleteCategory(categoryId: Long) {
        tasksDataRepository.deleteCategory(categoryId)
    }

    override suspend fun changeCategory(categoryId: Long?) {
        tasksDataRepository.changeCategory(categoryId)
    }

}
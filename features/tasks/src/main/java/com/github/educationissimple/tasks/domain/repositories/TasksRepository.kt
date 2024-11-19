package com.github.educationissimple.tasks.domain.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TasksRepository {

    suspend fun changeSelectionDate(date: LocalDate)

    suspend fun getNotCompletedTasksForDate() : Flow<ResultContainer<List<Task>>>

    suspend fun getCompletedTasksForDate() : Flow<ResultContainer<List<Task>>>

    suspend fun getPreviousTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun getTodayTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun getFutureTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun getCompletedTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun reloadTasks()

    suspend fun changeTaskSearchText(text: String)

    suspend fun getSelectedSortType() : Flow<ResultContainer<SortType?>>

    suspend fun changeSortType(sortType: SortType?)

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(taskId: Long)

    suspend fun getCategories(): Flow<ResultContainer<List<TaskCategory>>>

    suspend fun reloadCategories()

    suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>>

    suspend fun createCategory(name: String)

    suspend fun deleteCategory(categoryId: Long)

    suspend fun changeCategory(categoryId: Long?)

    suspend fun getAllReminders(): Flow<ResultContainer<List<TaskReminder>>>

    suspend fun reloadReminders()

    suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<TaskReminder>>>

    suspend fun createReminder(reminder: TaskReminder)

    suspend fun deleteReminder(reminder: TaskReminder)


}
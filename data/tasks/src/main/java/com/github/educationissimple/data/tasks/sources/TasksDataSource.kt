package com.github.educationissimple.data.tasks.sources

import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple
import java.time.LocalDate

interface TasksDataSource {

    suspend fun createTask(newTaskTuple: NewTaskTuple)

    suspend fun updateTask(taskDataEntity: TaskDataEntity)

    suspend fun deleteTask(id: Long)

    suspend fun getTasksBeforeDate(
        date: LocalDate,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null
    ): List<TaskDataEntity>

    suspend fun getTasksByDate(
        date: LocalDate,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null
    ): List<TaskDataEntity>

    suspend fun getTasksAfterDate(
        date: LocalDate,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null
    ): List<TaskDataEntity>

    suspend fun getCompletedTasks(
        date: LocalDate? = null,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null,
    ): List<TaskDataEntity>

    suspend fun getCategories(): List<TaskCategoryDataEntity>

    suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple)

    suspend fun deleteCategory(id: Long)

    suspend fun getReminders(): List<RemindersAndTasksTuple>

    suspend fun createTaskReminder(newReminderTuple: NewReminderTuple)

    suspend fun deleteTaskReminder(id: Long)

}
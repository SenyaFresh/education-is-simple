package com.github.educationissimple.data.tasks.sources

import android.content.Context
import androidx.room.Room
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.sources.room.TasksDatabase
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.utils.getMaxDate
import com.github.educationissimple.data.tasks.utils.getMinDate
import java.time.LocalDate
import javax.inject.Inject

class RoomTasksDataSource @Inject constructor(
    context: Context
) : TasksDataSource {

    private val db: TasksDatabase by lazy<TasksDatabase> {
        Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks.db"
        )
            .createFromAsset("initial_tasks_database.db")
            .build()
    }

    private val tasksDao = db.getTasksDao()
    private val tasksCategoryDao = db.getTasksCategoryDao()

    override suspend fun createTask(newTaskTuple: NewTaskTuple) {
        tasksDao.createTask(newTaskTuple)
    }

    override suspend fun updateTask(taskDataEntity: TaskDataEntity) {
        tasksDao.updateTask(taskDataEntity)
    }

    override suspend fun deleteTask(id: Long) {
        tasksDao.deleteTask(id)
    }

    override suspend fun getTasksBeforeDate(
        date: LocalDate,
        categoryId: Long?,
        sortType: String?
    ): List<TaskDataEntity> {
        return tasksDao.getTasks(
            getMinDate(),
            date.minusDays(1),
            false,
            categoryId,
            sortType
        )
    }

    override suspend fun getTasksByDate(
        date: LocalDate,
        categoryId: Long?,
        sortType: String?
    ): List<TaskDataEntity> {
        return tasksDao.getTasks(
            date,
            date,
            false,
            categoryId,
            sortType
        )
    }

    override suspend fun getTasksAfterDate(
        date: LocalDate,
        categoryId: Long?,
        sortType: String?
    ): List<TaskDataEntity> {
        return tasksDao.getTasks(
            date.plusDays(1),
            getMaxDate(),
            false,
            categoryId,
            sortType
        )
    }

    override suspend fun getCompletedTasks(categoryId: Long?, sortType: String?): List<TaskDataEntity> {
        return tasksDao.getTasks(
            getMinDate(),
            getMaxDate(),
            true,
            categoryId,
            sortType
        )
    }

    override suspend fun getCategories(): List<TaskCategoryDataEntity> {
        return tasksCategoryDao.getCategories()
    }

    override suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple) {
        return tasksCategoryDao.createCategory(newTaskCategoryTuple)
    }

    override suspend fun deleteCategory(id: Long) {
        deleteCategory(id)
    }

}
package com.github.educationissimple.data.tasks.sources

import android.content.Context
import androidx.room.Room
import com.github.educationissimple.data.tasks.converters.DateConverter
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.sources.room.TasksDao
import com.github.educationissimple.data.tasks.sources.room.TasksDatabase
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.TaskCompletionTuple
import java.time.LocalDate
import java.time.LocalDateTime

class RoomTasksDataSource(context: Context) : TasksDataSource {

    private val db: TasksDatabase by lazy<TasksDatabase> {
        Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks.db"
        ).build()
    }

    private val tasksDao = db.getTasksDao()
    private val converter = DateConverter()

    override suspend fun setTaskCompletion(taskCompletionTuple: TaskCompletionTuple) {
        tasksDao.setTaskCompletion(taskCompletionTuple)
    }

    override suspend fun createTask(newTaskTuple: NewTaskTuple) {
        tasksDao.createTask(newTaskTuple)
    }

    override suspend fun deleteTask(id: Long) {
        tasksDao.deleteTask(id)
    }

    override suspend fun getTasksBeforeDate(date: LocalDate): List<TaskDataEntity> {
        return tasksDao.getTasksBeforeDate(
            converter.dateToTimestamp(date) ?: throw IllegalArgumentException("Incorrect date")
        )
    }

    override suspend fun getTasksByDate(date: LocalDate): List<TaskDataEntity> {
        return tasksDao.getTasksByDate(
            converter.dateToTimestamp(date) ?: throw IllegalArgumentException("Incorrect date")
        )
    }

    override suspend fun getTasksAfterDate(date: LocalDate): List<TaskDataEntity> {
        return tasksDao.getTasksAfterDate(
            converter.dateToTimestamp(date) ?: throw IllegalArgumentException("Incorrect date")
        )
    }

    override suspend fun getCompletedTasks(): List<TaskDataEntity> {
        return tasksDao.getCompletedTasks()
    }

}
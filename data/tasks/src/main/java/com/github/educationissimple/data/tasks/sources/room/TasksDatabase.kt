package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.educationissimple.data.tasks.converters.DateConverter
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.entities.TaskReminderDataEntity

/**
 * Room database for managing tasks, task categories, and task reminders.
 *
 * This database defines the entities and DAOs used for interacting with the task-related data.
 * It includes the tables for tasks, task categories, and task reminders.
 */
@Database(
    version = 1,
    entities = [TaskDataEntity::class, TaskCategoryDataEntity::class, TaskReminderDataEntity::class]
)
@TypeConverters(DateConverter::class)
abstract class TasksDatabase : RoomDatabase() {

    /**
     * Provides access to the DAO for tasks.
     *
     * @return A [TasksDao] instance to interact with the tasks table.
     */
    abstract fun getTasksDao(): TasksDao

    /**
     * Provides access to the DAO for task categories.
     *
     * @return A [TasksCategoryDao] instance to interact with the task categories table.
     */
    abstract fun getTasksCategoryDao(): TasksCategoryDao

    /**
     * Provides access to the DAO for task reminders.
     *
     * @return A [TasksRemindersDao] instance to interact with the task reminders table.
     */
    abstract fun getTasksRemindersDao(): TasksRemindersDao

}
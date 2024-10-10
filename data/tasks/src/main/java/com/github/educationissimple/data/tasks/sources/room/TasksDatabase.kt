package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.educationissimple.data.tasks.converters.DateConverter
import com.github.educationissimple.data.tasks.entities.TaskDataEntity

@Database(
    version = 1,
    entities = [TaskDataEntity::class]
)
@TypeConverters(DateConverter::class)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TasksDao

}
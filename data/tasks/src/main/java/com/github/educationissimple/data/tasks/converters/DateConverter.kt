package com.github.educationissimple.data.tasks.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class DateConverter {

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun dateFromTimestamp(value: String?): LocalDate? {
        return value?.let {
            LocalDate.parse(it)
        }
    }

    @TypeConverter
    fun datetimeToTimestamp(datetime: LocalDateTime?): String? {
        return datetime?.toString()
    }

    @TypeConverter
    fun datetimeFromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it)
        }
    }

}
package com.github.educationissimple.data.tasks.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Type converters to handle conversion of LocalDate and LocalDateTime to and from String representation.
 * These converters are needed for Room database, as it doesn't support these types directly.
 */
class DateConverter {

    /**
     * Converts a [LocalDate] to a String for Room database storage.
     *
     * @param date The [LocalDate] to convert.
     * @return A string representation of the date, or null if the date is null.
     */
    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }

    /**
     * Converts a String (representing a [LocalDate]) back to a [LocalDate] object.
     *
     * @param value The String value to convert.
     * @return A [LocalDate] object or null if the value is null or invalid.
     */
    @TypeConverter
    fun dateFromTimestamp(value: String?): LocalDate? {
        return value?.let {
            LocalDate.parse(it)
        }
    }

    /**
     * Converts a [LocalDateTime] to a String for Room database storage.
     *
     * @param datetime The [LocalDateTime] to convert.
     * @return A string representation of the date, or null if the date is null.
     */
    @TypeConverter
    fun datetimeToTimestamp(datetime: LocalDateTime?): String? {
        return datetime?.toString()
    }

    /**
     * Converts a String (representing a [LocalDateTime]) back to a [LocalDateTime] object.
     *
     * @param value The String value to convert.
     * @return A [LocalDateTime] object or null if the value is null or invalid.
     */
    @TypeConverter
    fun datetimeFromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            LocalDateTime.parse(it)
        }
    }

}
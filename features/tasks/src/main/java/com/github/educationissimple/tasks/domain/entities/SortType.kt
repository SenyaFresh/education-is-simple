package com.github.educationissimple.tasks.domain.entities

/**
 * Represents different sorting options.
 *
 * Each sort type is represented as a distinct object with a specific string value.
 *
 * @property value The string representation of the sort type.
 */
sealed class SortType(val value: String) {

    /**
     * Represents sorting by date in ascending order.
     */
    data object DateAscending : SortType(value = "date_asc")

    /**
     * Represents sorting by date in descending order.
     */
    data object DateDescending : SortType(value = "date_desc")

    /**
     * Represents sorting by priority.
     */
    data object Priority: SortType(value = "priority")

    /**
     * Represents sorting by text in ascending order.
     */
    data object TextAscending: SortType(value = "text_asc")

    /**
     * Represents sorting by text in descending order.
     */
    data object TextDescending: SortType(value = "text_desc")

    companion object {
        /**
         * Converts a string value to the corresponding [SortType].
         *
         * @param value The string representation of a sort type.
         * @return The corresponding [SortType], or `null` if the string does not match any type.
         */
        fun fromString(value: String?): SortType? {
            return when (value) {
                "date_asc" -> DateAscending
                "date_desc" -> DateDescending
                "priority" -> Priority
                "text_asc" -> TextAscending
                "text_desc" -> TextDescending
                else -> null
            }
        }
    }

}
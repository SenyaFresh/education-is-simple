package com.github.educationissimple.tasks.domain.entities

sealed class SortType(val value: String) {

    data object DateAscending : SortType(value = "date_asc")

    data object DateDescending : SortType(value = "date_desc")

    data object Priority: SortType(value = "priority")

    data object TextAscending: SortType(value = "text_asc")

    data object TextDescending: SortType(value = "text_desc")

    companion object {
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
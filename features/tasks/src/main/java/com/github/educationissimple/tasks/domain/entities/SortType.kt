package com.github.educationissimple.tasks.domain.entities

sealed class SortType(val value: String) {

    data object Date : SortType(value = "date")

    data object Priority : SortType(value = "priority")

    data object Text: SortType(value = "text")

}
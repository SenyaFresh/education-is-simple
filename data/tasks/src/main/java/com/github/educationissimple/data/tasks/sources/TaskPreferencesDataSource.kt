package com.github.educationissimple.data.tasks.sources

interface TaskPreferencesDataSource {

    fun saveSelectedCategoryId(id: Long?)
    fun getSelectedCategoryId(): Long?

    fun saveSortType(sortType: String?)
    fun getSortType(): String?

    companion object {
        const val SELECTED_TASK_CATEGORY_ID = "selected_task_category_id"
        const val TASK_SORT_TYPE = "task_sort_type"
    }

}
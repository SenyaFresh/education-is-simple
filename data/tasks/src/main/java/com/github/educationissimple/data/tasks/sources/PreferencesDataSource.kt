package com.github.educationissimple.data.tasks.sources

interface PreferencesDataSource {

    fun saveSelectedCategoryId(id: Long?)
    fun getSelectedCategoryId(): Long?

    fun saveSortType(sortType: String?)
    fun getSortType(): String?

    companion object {
        const val SELECTED_CATEGORY_ID = "selected_category_id"
        const val SORT_TYPE = "sort_type"
    }

}
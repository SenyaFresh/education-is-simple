package com.github.educationissimple.data.tasks.sources

import android.content.Context
import javax.inject.Inject

class SharedTaskPreferencesDataSource @Inject constructor(
    context: Context
) : TaskPreferencesDataSource {

    private val sharedPreferences = context.getSharedPreferences(
        "task_preferences",
        Context.MODE_PRIVATE
    )

    override fun saveSelectedCategoryId(id: Long?) {
        sharedPreferences.edit()
            .putLong(
                TaskPreferencesDataSource.SELECTED_TASK_CATEGORY_ID,
                id ?: -1L
            )
            .apply()
    }

    override fun getSelectedCategoryId(): Long? {
        val selectedCategoryId = sharedPreferences.getLong(
            TaskPreferencesDataSource.SELECTED_TASK_CATEGORY_ID,
            -1L
        )
        return if (selectedCategoryId == -1L) null else selectedCategoryId
    }

    override fun saveSortType(sortType: String?) {
        sharedPreferences.edit()
            .putString(TaskPreferencesDataSource.TASK_SORT_TYPE, sortType ?: "")
            .apply()
    }

    override fun getSortType(): String? {
        val sortType = sharedPreferences.getString(
            TaskPreferencesDataSource.TASK_SORT_TYPE,
            null
        )
        return if (sortType.isNullOrEmpty()) null else sortType
    }
}
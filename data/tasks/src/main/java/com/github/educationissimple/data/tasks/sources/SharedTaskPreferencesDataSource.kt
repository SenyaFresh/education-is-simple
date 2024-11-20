package com.github.educationissimple.data.tasks.sources

import android.content.Context
import javax.inject.Inject

/**
 * Implementation of [TaskPreferencesDataSource] using SharedPreferences for storing task-related preferences.
 *
 * This class provides methods for saving and retrieving task preferences, such as the selected task category ID
 * and the sort type for tasks, using SharedPreferences for persistence.
 */
class SharedTaskPreferencesDataSource @Inject constructor(
    context: Context
) : TaskPreferencesDataSource {

    /**
     * SharedPreferences instance for storing task-related preferences.
     */
    private val sharedPreferences = context.getSharedPreferences(
        "task_preferences",
        Context.MODE_PRIVATE
    )

    /**
     * Saves the ID of the selected task category.
     *
     * @param id The ID of the selected task category, or null to clear the selection.
     */
    override fun saveSelectedCategoryId(id: Long?) {
        sharedPreferences.edit()
            .putLong(
                TaskPreferencesDataSource.SELECTED_TASK_CATEGORY_ID,
                id ?: -1L
            )
            .apply()
    }

    /**
     * Retrieves the ID of the selected task category.
     *
     * @return The ID of the selected task category, or null if no category is selected.
     */
    override fun getSelectedCategoryId(): Long? {
        val selectedCategoryId = sharedPreferences.getLong(
            TaskPreferencesDataSource.SELECTED_TASK_CATEGORY_ID,
            -1L
        )
        return if (selectedCategoryId == -1L) null else selectedCategoryId
    }

    /**
     * Saves the sort type for tasks.
     *
     * @param sortType The sort type to save, or null to clear the sort preference.
     */
    override fun saveSortType(sortType: String?) {
        sharedPreferences.edit()
            .putString(TaskPreferencesDataSource.TASK_SORT_TYPE, sortType ?: "")
            .apply()
    }

    /**
     * Retrieves the current sort type for tasks.
     *
     * @return The sort type string, or null if no sort type is saved.
     */
    override fun getSortType(): String? {
        val sortType = sharedPreferences.getString(
            TaskPreferencesDataSource.TASK_SORT_TYPE,
            null
        )
        return if (sortType.isNullOrEmpty()) null else sortType
    }
}
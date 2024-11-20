package com.github.educationissimple.data.tasks.sources

/**
 * Interface for managing task-related preferences such as selected category ID and sort type.
 *
 * This interface provides methods to save and retrieve preferences related to task management,
 * including the selected task category and the sort type for tasks.
 */
interface TaskPreferencesDataSource {

    /**
     * Saves the ID of the selected task category.
     *
     * @param id The ID of the selected task category, or null to clear the selection.
     */
    fun saveSelectedCategoryId(id: Long?)

    /**
     * Retrieves the ID of the selected task category.
     *
     * @return The ID of the selected task category, or null if no category is selected.
     */
    fun getSelectedCategoryId(): Long?

    /**
     * Saves the sort type for tasks.
     *
     * @param sortType The sort type to be saved, or null to clear the sort type.
     */
    fun saveSortType(sortType: String?)

    /**
     * Retrieves the sort type for tasks.
     *
     * @return The sort type for tasks, or null if no sort type is set.
     */
    fun getSortType(): String?

    companion object {
        /**
         * Key for storing and retrieving the selected task category ID in preferences.
         */
        const val SELECTED_TASK_CATEGORY_ID = "selected_task_category_id"

        /**
         * Key for storing and retrieving the sort type for tasks in preferences.
         */
        const val TASK_SORT_TYPE = "task_sort_type"
    }

}
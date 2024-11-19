package com.github.educationissimple.audio.sources

/**
 * Interface for managing audio-related user preferences.
 *
 * Defines methods for saving and retrieving the ID of the selected audio category.
 */
interface AudioPreferencesDataSource {

    /**
     * Saves the selected audio category ID in the shared preferences.
     *
     * @param id The ID of the selected audio category. Pass `null` to clear the selection.
     */
    fun saveSelectedCategoryId(id: Long?)

    /**
     * Retrieves the currently selected audio category ID from the shared preferences.
     *
     * @return The ID of the selected category, or `null` if no category is selected.
     */
    fun getSelectedCategoryId(): Long?

    companion object {
        /**
         * Constant key for storing the selected audio category ID in preferences.
         */
        const val SELECTED_AUDIO_CATEGORY_ID = "selected_audio_category_id"
    }
}
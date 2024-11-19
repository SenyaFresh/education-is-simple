package com.github.educationissimple.audio.sources

import android.content.Context
import javax.inject.Inject

/**
 * Implementation of [AudioPreferencesDataSource] for storing and retrieving the selected audio category ID.
 *
 * @param context The application context used to access the shared preferences.
 */
class SharedAudioPreferencesDataSource @Inject constructor(
    context: Context
) : AudioPreferencesDataSource {

    // SharedPreferences instance to store audio preferences.
    private val sharedPreferences = context.getSharedPreferences(
        "audio_preferences",
        Context.MODE_PRIVATE
    )

    /**
     * Saves the selected audio category ID in the shared preferences.
     *
     * @param id The ID of the selected audio category. Pass `null` to clear the selection.
     * If the provided `id` is null, it saves a default value of `-1L` to indicate no selection.
     */
    override fun saveSelectedCategoryId(id: Long?) {
        sharedPreferences.edit()
            .putLong(
                AudioPreferencesDataSource.SELECTED_AUDIO_CATEGORY_ID,
                id ?: -1L
            )
            .apply()
    }

    /**
     * Retrieves the currently selected audio category ID from the shared preferences.
     *
     * @return The ID of the selected category, or `null` if no category is selected.
     */
    override fun getSelectedCategoryId(): Long? {
        val selectedCategoryId = sharedPreferences.getLong(
            AudioPreferencesDataSource.SELECTED_AUDIO_CATEGORY_ID,
            -1L
        )
        return if (selectedCategoryId == -1L) null else selectedCategoryId
    }
}
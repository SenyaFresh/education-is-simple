package com.github.educationissimple.audio.sources.room

import android.content.Context
import com.github.educationissimple.audio.sources.AudioPreferencesDataSource
import javax.inject.Inject

class SharedAudioPreferencesDataSource @Inject constructor(
    context: Context
) : AudioPreferencesDataSource {

    private val sharedPreferences = context.getSharedPreferences(
        "audio_preferences",
        Context.MODE_PRIVATE
    )

    override fun saveSelectedCategoryId(id: Long?) {
        sharedPreferences.edit()
            .putLong(
                AudioPreferencesDataSource.SELECTED_AUDIO_CATEGORY_ID,
                id ?: -1L
            )
            .apply()
    }

    override fun getSelectedCategoryId(): Long? {
        val selectedCategoryId = sharedPreferences.getLong(
            AudioPreferencesDataSource.SELECTED_AUDIO_CATEGORY_ID,
            -1L
        )
        return if (selectedCategoryId == -1L) null else selectedCategoryId
    }
}
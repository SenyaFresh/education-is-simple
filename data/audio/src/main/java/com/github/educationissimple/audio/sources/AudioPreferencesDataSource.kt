package com.github.educationissimple.audio.sources

interface AudioPreferencesDataSource {

    fun saveSelectedCategoryId(id: Long?)

    fun getSelectedCategoryId(): Long?

    companion object {
        const val SELECTED_AUDIO_CATEGORY_ID = "selected_audio_category_id"
    }
}
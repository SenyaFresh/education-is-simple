package com.github.educationissimple.data.tasks.sources

import android.content.Context
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(
    context: Context
) : PreferencesDataSource {

    private val sharedPreferences = context.getSharedPreferences(
        "preferences",
        Context.MODE_PRIVATE
    )

    override fun saveSelectedCategoryId(id: Long?) {
        sharedPreferences.edit()
            .putLong(
                PreferencesDataSource.SELECTED_CATEGORY_ID,
                id ?: -1L
            )
            .apply()
    }

    override fun getSelectedCategoryId(): Long? {
        val selectedCategoryId = sharedPreferences.getLong(
            PreferencesDataSource.SELECTED_CATEGORY_ID,
            -1L
        )
        return if (selectedCategoryId == -1L) null else selectedCategoryId
    }

    override fun saveSortType(sortType: String?) {
        sharedPreferences.edit()
            .putString(PreferencesDataSource.SORT_TYPE, sortType ?: "")
            .apply()
    }

    override fun getSortType(): String? {
        val sortType = sharedPreferences.getString(
            PreferencesDataSource.SORT_TYPE,
            null
        )
        return if (sortType.isNullOrEmpty()) null else sortType
    }
}
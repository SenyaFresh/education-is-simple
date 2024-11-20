package com.github.educationissimple.audio.domain.usecases.categories

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

/**
 * Use case for deleting a category.
 */
class DeleteCategoryUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    /**
     * Deletes the category with the given ID.
     *
     * @param categoryId The ID of the category to delete.
     */
    suspend fun deleteCategory(categoryId: Long) {
        audioRepository.deleteCategory(categoryId)
    }

}
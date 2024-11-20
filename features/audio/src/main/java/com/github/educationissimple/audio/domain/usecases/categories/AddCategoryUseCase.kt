package com.github.educationissimple.audio.domain.usecases.categories

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

/**
 * Use case for adding a new category.
 */
class AddCategoryUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    /**
     * Adds a new category with the given name.
     *
     * @param name The name of the new category.
     */
    suspend fun addCategory(name: String) {
        audioRepository.createCategory(name)
    }

}
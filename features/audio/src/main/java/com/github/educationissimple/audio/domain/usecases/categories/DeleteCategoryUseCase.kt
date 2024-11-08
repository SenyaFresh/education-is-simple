package com.github.educationissimple.audio.domain.usecases.categories

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    suspend fun deleteCategory(categoryId: Long) {
        audioRepository.deleteCategory(categoryId)
    }

}
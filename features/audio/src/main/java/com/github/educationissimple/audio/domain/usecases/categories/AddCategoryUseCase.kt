package com.github.educationissimple.audio.domain.usecases.categories

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    suspend fun addCategory(name: String) {
        audioRepository.createCategory(name)
    }

}
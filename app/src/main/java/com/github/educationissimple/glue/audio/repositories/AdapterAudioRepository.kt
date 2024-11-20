package com.github.educationissimple.glue.audio.repositories

import android.app.Application
import android.net.Uri
import com.github.educationissimple.R
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.repositories.AudioDataRepository
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.UserFriendlyException
import com.github.educationissimple.glue.audio.mappers.mapToAudioCategory
import com.github.educationissimple.glue.audio.mappers.toAudio
import com.github.educationissimple.glue.audio.mappers.toAudioDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Adapter class for the [AudioRepository] interface that interacts with the [AudioDataRepository].
 * It acts as a middle layer that translates the requests from the application logic into calls
 * to the data repository, which handles the actual data operations such as fetching, adding, or
 * deleting audio items and categories.
 *
 * @param audioDataRepository The repository responsible for data storage and retrieval for audio-related data.
 * @param application The application context, used for tasks such as URI parsing and content resolver access.
 */
class AdapterAudioRepository @Inject constructor(
    private val audioDataRepository: AudioDataRepository,
    private val application: Application
) : AudioRepository {

    /**
     * Fetches a list of all audio items.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping a list of [Audio] objects.
     */
    override suspend fun getAudioItems(): Flow<ResultContainer<List<Audio>>> {
        return audioDataRepository.getAudio()
            .map { container -> container.map { list -> list.map { it.toAudio() } } }
    }

    /**
     * Reloads the audio data. This method is used to refresh the audio data in the repository.
     */
    override suspend fun reloadAudioItems() {
        audioDataRepository.reloadAudio()
    }

    /**
     * Adds a new audio item to the repository.
     * This method parses the provided URI to an [AudioDataEntity], adds the provided category ID (if any),
     * and then saves it in the data repository.
     *
     * @param uri The URI of the audio file to be added.
     * @param categoryId The category ID to associate with the audio item, can be null if no category is provided.
     * @throws UserFriendlyException If the audio URI could not be parsed or an error occurs while adding it.
     */
    override suspend fun addAudioItem(uri: String, categoryId: Long?) {
        val audio = Uri.parse(uri).toAudioDataEntity(application)?.copy(categoryId = categoryId)
        audioDataRepository.addAudio(
            audio ?: throw UserFriendlyException(Core.resources.getString(R.string.add_audio_error))
        )
    }

    /**
     * Deletes an audio item from the repository.
     *
     * @param uri The URI of the audio item to be deleted.
     */
    override suspend fun deleteAudioItem(uri: String) {
        audioDataRepository.deleteAudio(uri)
    }

    /**
     * Fetches all audio categories.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping a list of [AudioCategory] objects.
     */
    override suspend fun getCategories(): Flow<ResultContainer<List<AudioCategory>>> {
        return audioDataRepository.getCategories().mapToAudioCategory()
    }

    /**
     * Reloads the audio categories.
     */
    override suspend fun reloadCategories() {
        audioDataRepository.reloadCategories()
    }

    /**
     * Creates a new audio category.
     *
     * @param name The name of the new category.
     */
    override suspend fun createCategory(name: String) {
        audioDataRepository.createCategory(NewAudioCategoryTuple(name))
    }

    /**
     * Deletes an audio category.
     *
     * @param categoryId The ID of the category to be deleted.
     */
    override suspend fun deleteCategory(categoryId: Long) {
        audioDataRepository.deleteCategory(categoryId)
    }

    /**
     * Changes the currently selected audio category.
     *
     * @param categoryId The ID of the category to be selected.
     */
    override suspend fun changeCategory(categoryId: Long?) {
        audioDataRepository.changeCategory(categoryId)
    }

    /**
     * Retrieves the ID of the currently selected audio category.
     * @return A [Flow] emitting a [ResultContainer] wrapping the category ID as a nullable [Long].
     * The result will contain the category ID or null value if no category is selected.
     */
    override suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return audioDataRepository.getSelectedCategoryId()
    }

}
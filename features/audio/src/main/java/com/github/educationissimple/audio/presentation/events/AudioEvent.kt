package com.github.educationissimple.audio.presentation.events

import com.github.educationissimple.audio.domain.entities.PlayerController

/**
 * Sealed class representing various events related to audio handling.
 *
 * This class encapsulates different actions that can be performed on audio items and categories.
 * Each event represents a specific action or change that should be processed in the viewmodel.
 */
sealed class AudioEvent {

    /**
     * Event to handle player-related actions such as play, pause, skip, etc.
     *
     * @param controller A [PlayerController] instance that defines the specific action to be executed (e.g., play/pause, seek).
     */
    data class PlayerEvent(val controller: PlayerController) : AudioEvent()

    /**
     * Event to add a new audio item to the list.
     *
     * @param uri The URI of the audio item to be added.
     * @param categoryId The optional category ID associated with the audio item. Can be null.
     */
    data class AddAudioItemEvent(val uri: String, val categoryId: Long?) : AudioEvent()

    /**
     * Event to delete an existing audio item by its URI.
     *
     * @param uri The URI of the audio item to be deleted.
     */
    data class DeleteAudioItemEvent(val uri: String) : AudioEvent()

    /**
     * Event to create a new audio category.
     *
     * @param name The name of the new audio category.
     */
    data class CreateCategoryEvent(val name: String) : AudioEvent()

    /**
     * Event to delete an existing audio category by its ID.
     *
     * @param categoryId The ID of the audio category to be deleted.
     */
    data class DeleteCategoryEvent(val categoryId: Long) : AudioEvent()

    /**
     * Event to change the active category.
     *
     * @param categoryId The ID of the new active category. Can be null to indicate no category.
     */
    data class ChangeCategoryEvent(val categoryId: Long?) : AudioEvent()

    /**
     * Event to reload the list of audio items.
     */
    data object ReloadAudioItemsEvent : AudioEvent()

    /**
     * Event to reload the list of audio categories.
     */
    data object ReloadAudioCategoriesEvent : AudioEvent()

}
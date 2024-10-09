package com.github.educationissimple.common

import kotlinx.coroutines.CoroutineScope

/**
 * Provides global entities.
 */
interface CoreProvider {

    val errorHandler: ErrorHandler

    val globalScope: CoroutineScope

    val logger: Logger

    val resources: Resources

    val toaster: Toaster

    val remoteTimeoutMillis: Long get() = 10000L

    val debounceTimeoutMillis: Long get() = 200L
}
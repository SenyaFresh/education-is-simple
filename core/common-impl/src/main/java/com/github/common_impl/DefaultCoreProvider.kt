package com.github.common_impl

import android.content.Context
import com.github.common.CoreProvider
import com.github.common.Logger
import com.github.common.Resources
import com.github.common.Toaster
import com.github.common.ErrorHandler
import kotlinx.coroutines.CoroutineScope

class DefaultCoreProvider(
    private val appContext: Context,
    override val resources: Resources = AndroidResources(appContext),
    override val globalScope: CoroutineScope = createDefaultGlobalScope(),
    override val toaster: Toaster = AndroidToaster(appContext),
    override val logger: Logger = AndroidLogger(),
    override val errorHandler: ErrorHandler = DefaultErrorHandler(
        logger,
        resources,
        toaster
    )
) : CoreProvider
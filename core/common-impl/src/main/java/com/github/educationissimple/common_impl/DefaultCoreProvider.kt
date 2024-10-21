package com.github.educationissimple.common_impl

import android.content.Context
import com.github.educationissimple.common.CoreProvider
import com.github.educationissimple.common.ErrorHandler
import com.github.educationissimple.common.Logger
import com.github.educationissimple.common.Resources
import com.github.educationissimple.common.Toaster
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
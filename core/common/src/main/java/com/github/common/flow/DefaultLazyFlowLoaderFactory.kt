package com.github.common.flow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import ru.edu.hse.common.Core

class DefaultLazyFlowLoaderFactory(
    private val dispatcher: CoroutineDispatcher,
    private val globalScope: CoroutineScope = Core.globalScope,
    private val cacheTimeoutMillis: Long = 1000
) : LazyFlowLoaderFactory {

    override fun <T> create(loader: ValueLoader<T>): LazyFlowLoader<T> {
        return DefaultLazyFlowLoader(loader, dispatcher, globalScope, cacheTimeoutMillis)
    }

}
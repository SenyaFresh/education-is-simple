package com.github.common.flow

import com.github.common.Core
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class DefaultLazyFlowLoaderFactory(
    private val dispatcher: CoroutineDispatcher,
    private val globalScope: CoroutineScope = Core.globalScope,
    private val cacheTimeoutMillis: Long = 1000
) : LazyFlowLoaderFactory {

    override fun <T> create(loader: ValueLoader<T>): LazyFlowLoader<T> {
        return DefaultLazyFlowLoader(loader, dispatcher, globalScope, cacheTimeoutMillis)
    }

}
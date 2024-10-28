package com.github.educationissimple.common.flow

import com.github.educationissimple.common.Core
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class DefaultLazyFlowLoaderFactory(
    private val dispatcher: CoroutineDispatcher,
    private val globalScope: CoroutineScope = Core.globalScope,
    private val cacheTimeoutMillis: Long = 1000,
    private val loadingStatusTimeoutMillis: Long = 100
) : LazyFlowLoaderFactory {

    override fun <T> create(loader: ValueLoader<T>): LazyFlowLoader<T> {
        return DefaultLazyFlowLoader(
            loader,
            dispatcher,
            globalScope,
            cacheTimeoutMillis,
            loadingStatusTimeoutMillis
        )
    }

}
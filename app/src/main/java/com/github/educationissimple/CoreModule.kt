package com.github.educationissimple

import android.content.Context
import com.github.educationissimple.common.CoreProvider
import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.common.flow.DefaultLazyFlowLoaderFactory
import com.github.educationissimple.common.flow.LazyFlowLoaderFactory
import com.github.educationissimple.common_impl.DefaultCoreProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CoreModule {

    @Provides
    fun provideCoreProvider(
        context: Context
    ): CoreProvider {
        return DefaultCoreProvider(context)
    }

    @Provides
    @AppScope
    fun provideLazyFlowLoaderFactory() : LazyFlowLoaderFactory {
        return DefaultLazyFlowLoaderFactory(Dispatchers.IO)
    }

}
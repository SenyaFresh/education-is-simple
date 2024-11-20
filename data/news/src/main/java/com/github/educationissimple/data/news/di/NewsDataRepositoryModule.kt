package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.repositories.NewsDataRepository
import com.github.educationissimple.data.news.repositories.PagerNewsDataRepository
import dagger.Binds
import dagger.Module

/**
 * Main Dagger module that binds the [NewsDataRepository] interface to its implementation
 * and includes [NetworkModule], [NewsApiServiceModule], [NewsPagingSourceModule].
 *
 */
@Module(includes = [NetworkModule::class, NewsApiServiceModule::class, NewsPagingSourceModule::class])
interface NewsDataRepositoryModule {

    /**
     * Binds the [PagerNewsDataRepository] implementation to the [NewsDataRepository] interface.
     *
     * @param impl The [PagerNewsDataRepository] implementation.
     * @return A bound [NewsDataRepository] instance.
     */
    @Binds
    @AppScope
    fun bindNewsRepository(impl: PagerNewsDataRepository): NewsDataRepository

}
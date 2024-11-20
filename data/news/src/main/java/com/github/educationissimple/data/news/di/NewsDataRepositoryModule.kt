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
     * This method ensures that whenever a [NewsDataRepository] is needed, the [PagerNewsDataRepository]
     * implementation is provided. This allows the repository to handle network requests for news articles
     * using a paging approach.
     *
     * @param impl The [PagerNewsDataRepository] implementation.
     * @return A bound [NewsDataRepository] instance.
     */
    @Binds
    @AppScope
    fun bindNewsRepository(impl: PagerNewsDataRepository): NewsDataRepository

}
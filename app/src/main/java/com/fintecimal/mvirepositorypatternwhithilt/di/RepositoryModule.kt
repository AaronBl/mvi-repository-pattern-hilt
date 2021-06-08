package com.fintecimal.mvirepositorypatternwhithilt.di

import com.fintecimal.mvirepositorypatternwhithilt.repository.MainRepository
import com.fintecimal.mvirepositorypatternwhithilt.retrofit.BlogRetrofit
import com.fintecimal.mvirepositorypatternwhithilt.retrofit.NetworkMapper
import com.fintecimal.mvirepositorypatternwhithilt.room.BlogDao
import com.fintecimal.mvirepositorypatternwhithilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}
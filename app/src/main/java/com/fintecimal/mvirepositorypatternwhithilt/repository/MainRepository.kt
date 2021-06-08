package com.fintecimal.mvirepositorypatternwhithilt.repository

import com.fintecimal.mvirepositorypatternwhithilt.model.Blog
import com.fintecimal.mvirepositorypatternwhithilt.retrofit.BlogRetrofit
import com.fintecimal.mvirepositorypatternwhithilt.retrofit.NetworkMapper
import com.fintecimal.mvirepositorypatternwhithilt.room.BlogDao
import com.fintecimal.mvirepositorypatternwhithilt.room.CacheMapper
import com.fintecimal.mvirepositorypatternwhithilt.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import  kotlinx.coroutines.flow.Flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
){
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for(blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}
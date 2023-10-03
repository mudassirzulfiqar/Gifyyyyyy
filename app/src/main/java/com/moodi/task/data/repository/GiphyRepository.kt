package com.moodi.task.data.repository

import com.moodi.task.data.Resource
import com.moodi.task.data.local.GiphyAppModel
import kotlinx.coroutines.flow.Flow

/**
 * This interface is used to define the methods to be implemented by [GiphyRepositoryImpl]
 * In order to get the data from remote sources.
 * The reason to use this approach is to make the code loosely coupled and testable.
 */
interface GiphyRepository {
     fun getRandomGif(): Flow<Resource<GiphyAppModel>>
     fun searchGif(query: String, limit: Int): Flow<Resource<List<GiphyAppModel>>>
}
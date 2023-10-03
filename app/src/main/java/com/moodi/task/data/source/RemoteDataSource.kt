package com.moodi.task.data.source

import com.moodi.task.data.Resource
import com.moodi.task.data.remote.model.RandomGiphyModelResponse
import com.moodi.task.data.remote.model.SearchGiphyModelResponse

/**
 * This interface is used to define the methods that will be used to fetch data from the server
 * In order to get the data from remote sources.
 * The reason to use this approach is to make the code loosely coupled and testable.
 * Responses are being handled by Resource class which is a sealed class it helps wrap responses for
 * success , error and loading cases.
 */
interface RemoteDataSource {
    suspend fun getRandomGif(): Resource<RandomGiphyModelResponse>
    suspend fun searchGif(query: String, limit: Int): Resource<SearchGiphyModelResponse>
}
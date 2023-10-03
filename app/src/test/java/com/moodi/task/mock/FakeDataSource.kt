package com.moodi.task.mock

import com.moodi.task.data.Resource
import com.moodi.task.data.remote.model.RandomGiphyModelResponse
import com.moodi.task.data.remote.model.SearchGiphyModelResponse
import com.moodi.task.data.source.RemoteDataSource

class FakeDataSource : RemoteDataSource {

        var testSearchGif: Resource<SearchGiphyModelResponse>? = null
        var testGetRandomGif: Resource<RandomGiphyModelResponse>? = null
        override suspend fun getRandomGif() = testGetRandomGif!!
        override suspend fun searchGif(query: String, limit: Int) = testSearchGif!!
    }

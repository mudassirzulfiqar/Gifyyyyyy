package com.moodi.data.mock

import com.moodi.data.remote.dto.RandomGiphyDTO
import com.moodi.data.remote.dto.SearchGiphyDTO
import com.moodi.data.source.RemoteDataSource
import com.moodi.domain.util.Resource

class FakeDataSource : RemoteDataSource {

    var testSearchGif: Resource<SearchGiphyDTO>? = null
    var testGetRandomGif: Resource<RandomGiphyDTO>? = null
    override suspend fun getRandomGif() = testGetRandomGif!!
    override suspend fun searchGif(query: String, limit: Int) = testSearchGif!!
}

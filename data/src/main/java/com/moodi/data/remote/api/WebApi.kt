package com.moodi.data.remote.api

import com.moodi.data.remote.config.Endpoint
import com.moodi.data.remote.dto.RandomGiphyDTO
import com.moodi.data.remote.dto.SearchGiphyDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface is used to define the API endpoints
 */
interface WebApi {
    companion object {
        /**
         * The maximum number of items that can be returned in a single search.
         */
        const val SEARCH_LIMIT_SIZE = 40
    }

    /**
     * This function is used to get the random gif
     */
    @GET(Endpoint.Route.RANDOM)
    suspend fun getRandomGif(): Response<RandomGiphyDTO>

    /**
     * This function is used to search the gif
     */
    @GET(Endpoint.Route.SEARCH)
    suspend fun searchGif(
        @Query("limit") limit: Int,
        @Query("q") query: String
    ): Response<SearchGiphyDTO>
}
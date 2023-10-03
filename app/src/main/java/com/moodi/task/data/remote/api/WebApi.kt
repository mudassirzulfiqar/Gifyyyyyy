package com.moodi.task.data.remote.api

import com.moodi.task.data.remote.config.Endpoint
import com.moodi.task.data.remote.model.RandomGiphyModelResponse
import com.moodi.task.data.remote.model.SearchGiphyModelResponse
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
    suspend fun getRandomGif(): Response<RandomGiphyModelResponse>

    /**
     * This function is used to search the gif
     */
    @GET(Endpoint.Route.SEARCH)
    suspend fun searchGif(
        @Query("limit") limit: Int,
        @Query("q") query: String
    ): Response<SearchGiphyModelResponse>
}
package com.moodi.data.source

import com.moodi.data.remote.api.WebApi
import com.moodi.domain.util.ERROR_NETWORK
import com.moodi.domain.util.Resource
import com.moodi.data.remote.dto.RandomGiphyDTO
import com.moodi.data.remote.dto.SearchGiphyDTO
import retrofit2.Response

/**
 * This class is used to fetch data from the server by injecting the webApi from injection.
 * This class is handling the responses from Retrofit as Success or Failure.
 *
 *  Note: I generally don't use any network validator checks before doing any api. Reason is that network checking libraries in
 * android are not reliable and usually chances are there and network check is passed and abruptly network disappears
 * so I prefer to execute API with try catch block. I have used this approach in many projects and my network logs are very much improved.
 * Considering time constraints Im catching all network exceptions with [ERROR_NETWORK] code.
 */
class RemoteDataSourceImpl(private val webApi: WebApi) : RemoteDataSource {

    override suspend fun getRandomGif(): Resource<RandomGiphyDTO> {
        return executeApiCall { webApi.getRandomGif() }
    }

    override suspend fun searchGif(query: String, limit: Int): Resource<SearchGiphyDTO> {
        return executeApiCall { webApi.searchGif(limit, query) }
    }

    /**
     * Generic helper function to handle API calls and responses.
     * Removes duplicate code and standardizes error handling.
     */
    private suspend fun <T> executeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.Success(body)
                } else {
                    Resource.Failure(response.code(), "Response body is null")
                }
            } else {
                Resource.Failure(response.code(), response.message())
            }
        } catch (e: Exception) {
            Resource.Failure(ERROR_NETWORK, e.message ?: "Unknown network error")
        }
    }
}
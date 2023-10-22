package com.moodi.data.remote.dto

/**
 * This data class is used to map the response from the Random Giphy API
 */
data class SearchGiphyDTO(
    val data: List<Data>
)
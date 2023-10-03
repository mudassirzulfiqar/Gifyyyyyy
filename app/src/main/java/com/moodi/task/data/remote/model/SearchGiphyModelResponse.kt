package com.moodi.task.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * This data class is used to map the response from the Random Giphy API
 */
data class SearchGiphyModelResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("pagination")
    val pagination: Pagination
)
package com.moodi.task.data.remote.model


import com.google.gson.annotations.SerializedName
/**
 * This data class is used to map the response from the Random Giphy API
 */
data class RandomGiphyModelResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("meta")
    val meta: Meta
)
package com.moodi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String?,
    val id: String?,
    val images: Images,
    val rating: String?,
    val title: String?,
) {
    data class Images(
        val downsized: Meta,
        @SerializedName("downsized_still")
        val downsizedStill: Meta,
    ) {
        data class Meta(
            val url: String?,
        )
    }

}


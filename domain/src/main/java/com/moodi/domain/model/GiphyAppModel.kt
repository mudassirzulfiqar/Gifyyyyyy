package com.moodi.domain.model

/**
 * This is the model class used in App Domain
 */
data class GiphyAppModel(
    /**
     * This is the id of the gif
     */
    val id: String,
    /**
     * This is the rating of the gif
     */
    val ageRate: String,
    /**
     * This is the title of the gif
     */
    val title: String,
    /**
     * This is the animated of the gif
     */
    val animatedUrl: String,
    /**
     * This is the short url of the gif
     */
    val displayLink: String,
    /**
     * This is the still of the gif
     */
    val stillUrl: String
)
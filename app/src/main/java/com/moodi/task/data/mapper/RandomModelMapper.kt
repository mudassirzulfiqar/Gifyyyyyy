package com.moodi.task.data.mapper

import com.moodi.task.data.local.GiphyAppModel
import com.moodi.task.data.remote.model.RandomGiphyModelResponse

/**
 * Implementation of DomainMapper to map the data from one layer to another
 * This class is used to map the data from RandomGiphyModelResponse to GiphyAppModel
 *
 * @see DomainMapper
 *
 * @property domain is the data from [RandomGiphyModelResponse]
 * @return GiphyAppModel
 */
object RandomModelMapper : DomainMapper<RandomGiphyModelResponse, GiphyAppModel> {
    override fun asAppModel(domain: RandomGiphyModelResponse): GiphyAppModel {
        domain.data.apply {
            return GiphyAppModel(
                id = id ?: "",
                ageRate = rating ?: "",
                title = title ?: "",
                /**
                 * Using downsized image as animated image
                 */
                animatedUrl = images.downsized.url ?: "",
                /**
                 * Using bitly_gif_url as display link as it is short url
                 */
                displayLink = bitlyGifUrl ?: "",
                /**
                 * Using downsized_still image as still image
                 */
                stillUrl = images.downsizedStill.url ?: ""
            )
        }
    }

}

/**
 * Extension function to convert RandomGiphyModelResponse to GiphyAppModel
 */
fun RandomGiphyModelResponse.asAppModel() = RandomModelMapper.asAppModel(this)
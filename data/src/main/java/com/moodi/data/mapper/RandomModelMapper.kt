package com.moodi.data.mapper

import com.moodi.domain.model.GiphyAppModel
import com.moodi.data.remote.dto.RandomGiphyDTO

/**
 * Implementation of DomainMapper to map the data from one layer to another
 * This class is used to map the data from RandomGiphyModelResponse to GiphyAppModel
 *
 * @see DomainMapper
 *
 * @property domain is the data from [RandomGiphyDTO]
 * @return GiphyAppModel
 */
object RandomModelMapper : DomainMapper<RandomGiphyDTO, GiphyAppModel> {
    override fun asAppModel(domain: RandomGiphyDTO): GiphyAppModel {
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
fun RandomGiphyDTO.asAppModel() = RandomModelMapper.asAppModel(this)
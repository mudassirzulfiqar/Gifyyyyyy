package com.moodi.data.mapper

import com.moodi.data.remote.dto.SearchGiphyDTO
import com.moodi.domain.model.GiphyAppModel

/**
 * Implementation of DomainMapper to map the data from one layer to another
 * This class is used to map the data from SearchGiphyModelResponse to GiphyAppModel
 */
object SearchModelMapper : DomainMapper<SearchGiphyDTO, List<GiphyAppModel>> {
    override fun asAppModel(domain: SearchGiphyDTO): List<GiphyAppModel> {
        val data = domain.data
        val list = mutableListOf<GiphyAppModel>()
        data.forEach {
            it.apply {
                list.add(
                    GiphyAppModel(
                        id = id ?: "",
                        ageRate = rating ?: "",
                        title = title ?: "",
                        animatedUrl = images.downsized.url ?: "",
                        displayLink = bitlyGifUrl ?: "",
                        stillUrl = images.downsizedStill.url ?: ""
                    )
                )
            }
        }
        return list
    }

}

/**
 * Extension function to convert SearchGiphyModelResponse to List<GiphyAppModel>
 */
fun SearchGiphyDTO.asAppModel() = SearchModelMapper.asAppModel(this)
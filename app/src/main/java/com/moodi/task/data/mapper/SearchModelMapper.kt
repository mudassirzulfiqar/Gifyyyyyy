package com.moodi.task.data.mapper

import com.moodi.task.data.local.GiphyAppModel
import com.moodi.task.data.remote.model.SearchGiphyModelResponse

/**
 * Implementation of DomainMapper to map the data from one layer to another
 * This class is used to map the data from SearchGiphyModelResponse to GiphyAppModel
 */
object SearchModelMapper : DomainMapper<SearchGiphyModelResponse, List<GiphyAppModel>> {
    override fun asAppModel(domain: SearchGiphyModelResponse): List<GiphyAppModel> {
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
fun SearchGiphyModelResponse.asAppModel() = SearchModelMapper.asAppModel(this)
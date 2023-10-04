package com.moodi.task.usecase

import com.moodi.task.data.repository.GiphyRepository

class SearchGifUseCase(private val giphyRepository: GiphyRepository) {
    operator fun invoke(search: String) = giphyRepository.searchGif(search, 25)

}
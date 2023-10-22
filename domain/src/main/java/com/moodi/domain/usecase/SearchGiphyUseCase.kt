package com.moodi.domain.usecase

import com.moodi.domain.repository.GiphyRepository

class SearchGiphyUseCase(val giphyRepository: GiphyRepository) {

    operator fun invoke(query: String, limit: Int) = giphyRepository.searchGif(query, limit)
}
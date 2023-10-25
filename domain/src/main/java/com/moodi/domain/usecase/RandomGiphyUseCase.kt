package com.moodi.domain.usecase

import com.moodi.domain.repository.GiphyRepository

class RandomGiphyUseCase(private val giphyRepository: GiphyRepository) {

    operator fun invoke() = giphyRepository.getRandomGif()
}
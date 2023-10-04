package com.moodi.task.usecase

import com.moodi.task.data.repository.GiphyRepository

class GetRandomGifUseCase(private val giphyRepository: GiphyRepository) {
    operator fun invoke() = giphyRepository.getRandomGif()

}
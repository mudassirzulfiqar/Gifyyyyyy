package com.moodi.domain.usecase

import com.moodi.domain.repository.GiphyRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class RandomGiphyUseCaseTest {

    @Test
    fun `invoke getRandomGif() from repository`() {

        val mockRepository = mockk<GiphyRepository>(relaxed = true)
        val randomGiphyUseCase = RandomGiphyUseCase(mockRepository)

        randomGiphyUseCase()

        verify(exactly = 1) { mockRepository.getRandomGif() }
    }
}
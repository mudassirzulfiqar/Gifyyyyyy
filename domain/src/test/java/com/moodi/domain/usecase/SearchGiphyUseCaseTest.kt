package com.moodi.domain.usecase

import com.moodi.domain.repository.GiphyRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SearchGiphyUseCaseTest {

    @Test
    fun `invoke searchGif from repository`() {

        val mockRepository = mockk<GiphyRepository>(relaxed = true)
        val searchGiphyUseCase = SearchGiphyUseCase(mockRepository)

        searchGiphyUseCase("query", 10)

        verify(exactly = 1) { mockRepository.searchGif("query", 10) }
    }


}
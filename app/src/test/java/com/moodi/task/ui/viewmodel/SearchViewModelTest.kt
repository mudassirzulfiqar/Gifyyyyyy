package com.moodi.task.ui.viewmodel

import app.cash.turbine.test
import com.moodi.common.API_DATA_SEARCH
import com.moodi.common.DispatcherProvider
import com.moodi.common.ErrorCode
import com.moodi.domain.usecase.SearchGiphyUseCase
import com.moodi.domain.util.Resource
import com.moodi.task.sate.search.UiEvent
import com.moodi.task.ui.TestDispatcherProvider
import com.moodi.task.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * This class is used to test the [SearchViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun `search() should return success State with data`() = runTest {
        val fakeRepository = FakeRepository(
            Resource.Success(listOf(API_DATA_SEARCH)),
            null
        )

        val useCase = SearchGiphyUseCase(fakeRepository)
        viewModel = SearchViewModel(useCase, dispatcherProvider)
        viewModel.onEvent(UiEvent.Search(SEARCH_QUERY))
        viewModel.dataState.test {
            val awaitResult = awaitItem()

            // assert if loading
            assert(awaitResult.loading)

            // assert data
            assert(awaitResult.data[0] == API_DATA_SEARCH)
        }
    }

    @Test
    fun `search() should return success State with no data`() = runTest {
        val fakeRepository = FakeRepository(
            Resource.Success(emptyList()),
            null
        )
        val useCase = SearchGiphyUseCase(fakeRepository)

        viewModel = SearchViewModel(useCase, dispatcherProvider)
        viewModel.onEvent(UiEvent.Search(SEARCH_QUERY))
        viewModel.dataState.test {
            val awaitResult = awaitItem()

            // assert data
            assert(awaitResult.data.isEmpty())
        }
    }

    @Test
    fun `search() should return error State`() = runTest {
        val fakeRepository = FakeRepository(
            Resource.Failure(
                ErrorCode.SERVER_ERROR,
                ""
            ),
            null
        )
        val useCase = SearchGiphyUseCase(fakeRepository)

        viewModel = SearchViewModel(useCase, dispatcherProvider)
        viewModel.onEvent(UiEvent.Search(SEARCH_QUERY))
        viewModel.dataState.test {
            val awaitResult = awaitItem()
            assert(awaitResult.error == ErrorCode.SERVER_ERROR.toString())
        }
    }

    @Test
    fun `clearSearch() should return empty State`() = runTest {
        val fakeRepository = FakeRepository(
            Resource.Failure(
                ErrorCode.SERVER_ERROR,
                ""
            ),
            null
        )
        val useCase = SearchGiphyUseCase(fakeRepository)

        viewModel = SearchViewModel(useCase, dispatcherProvider)
        viewModel.onEvent(UiEvent.ClearSearch)
        viewModel.dataState.test {
            // assert SearchState.Empty
            val awaitResult = awaitItem()
            // assert if not empty
            assert(awaitResult.data.isEmpty())
        }
    }

    companion object {
        const val SEARCH_QUERY = "test"
    }
}

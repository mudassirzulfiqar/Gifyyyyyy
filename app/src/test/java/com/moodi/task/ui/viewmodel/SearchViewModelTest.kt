package com.moodi.task.ui.viewmodel

import app.cash.turbine.test
import com.moodi.task.data.Resource
import com.moodi.task.data.mapper.asAppModel
import com.moodi.task.data.remote.model.SearchGiphyModelResponse
import com.moodi.task.mock.API_DATA_SEARCH
import com.moodi.task.mock.ErrorCode
import com.moodi.task.mock.FakeRepository
import com.moodi.task.mock.MockUtil
import com.moodi.task.mock.fromJson
import com.moodi.task.sate.search.SearchState
import com.moodi.task.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * This class is used to test the [SearchViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private var mockUtil = MockUtil()
    private lateinit var viewModel: SearchViewModel

    // Executes each task synchronously using Architecture Components.
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search() should return success State with data`() = runTest {

        val fakeRepository = FakeRepository(
            Resource.Success(
                mockUtil
                    .convertJsonToString(MockUtil.SEARCH_MODEL_LIST)
                    .fromJson<SearchGiphyModelResponse>()
                    .asAppModel()
            ), null
        )

        viewModel = SearchViewModel(fakeRepository)
        viewModel.search(SEARCH_QUERY)
        viewModel.dataState.test {

            // assert if SearchState.Empty
            val awaitEmpty = awaitItem()
            assert(awaitEmpty is SearchState.Empty)

            // assert if SearchState.Loading
            val awaitLoading = awaitItem()
            assert(awaitLoading is SearchState.Loading)

            // assert if SearchState.Success
            val awaitSuccess = awaitItem()
            assert(awaitSuccess is SearchState.Success)

            // assert to check 5 results
            assert((awaitSuccess as SearchState.Success).data.size == 2)

            // assert data

            assert(awaitSuccess.data[0] == API_DATA_SEARCH)

        }
    }

    @Test
    fun `search() should return success State with no data`() = runTest {

        val fakeRepository = FakeRepository(
            Resource.Success(
                mockUtil
                    .convertJsonToString(MockUtil.SEARCH_MODEL_EMPTY)
                    .fromJson<SearchGiphyModelResponse>()
                    .asAppModel()

            ), null
        )

        viewModel = SearchViewModel(fakeRepository)
        viewModel.search(SEARCH_QUERY)
        viewModel.dataState.test {

            // assert SearchState.Empty
            val awaitEmpty = awaitItem()
            assert(awaitEmpty is SearchState.Empty)

            // assert if it is SearchState.Loading
            val awaitLoading = awaitItem()
            assert(awaitLoading is SearchState.Loading)

            // assert if SearchState.NoSearchResults
            val awaitSuccess = awaitItem()
            assert(awaitSuccess is SearchState.NoSearchResults)
        }
    }

    @Test
    fun `search() should return error State`() = runTest {

        val fakeRepository = FakeRepository(
            Resource.Failure(
                ErrorCode.SERVER_ERROR,
                ""
            ), null
        )

        viewModel = SearchViewModel(fakeRepository)
        viewModel.search(SEARCH_QUERY)
        viewModel.dataState.test {

            // assert SearchState.Empty
            val awaitEmpty = awaitItem()
            assert(awaitEmpty is SearchState.Empty)

            // assert if it is SearchState.Loading
            val awaitLoading = awaitItem()
            assert(awaitLoading is SearchState.Loading)

            // assert if SearchState.NetworkError
            val awaitSuccess = awaitItem()
            assert(awaitSuccess is SearchState.NetworkError)

            // assert if SearchState.NetworkError has error code 500
            assert((awaitSuccess as SearchState.NetworkError).code == ErrorCode.SERVER_ERROR)
        }
    }

    @Test
    fun `clearSearch() should return empty State`() = runTest {

        val fakeRepository = FakeRepository(
            Resource.Failure(
                ErrorCode.SERVER_ERROR,
                ""
            ), null
        )

        viewModel = SearchViewModel(fakeRepository)
        viewModel.clearSearch()
        viewModel.dataState.test {

            // assert SearchState.Empty
            val awaitEmpty = awaitItem()
            assert(awaitEmpty is SearchState.Empty)
        }
    }

    companion object {
        const val SEARCH_QUERY = "test"
    }

}
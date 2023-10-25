package com.moodi.task.ui.viewmodel

import com.moodi.task.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This class is used to test the [SearchViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    /*

        private var mockUtil = MockUtil()
        private lateinit var viewModel: SearchViewModel
        private lateinit var useCase: SearchGiphyUseCase

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

            viewModel = SearchViewModel(useCase)
            viewModel.onEvent(UiEvent.Search(SEARCH_QUERY))
            viewModel.dataState.test {
                val awaitResult = awaitItem()
                // assert if not empty
                assert(awaitResult.data.isNotEmpty())

                // assert if loading
                assert(awaitResult.loading)

                // assert data
    //            assert(awaitResult.data[0] == API_DATA_SEARCH)
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

                ),
                null
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
                ),
                null
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
                ),
                null
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
    */
}

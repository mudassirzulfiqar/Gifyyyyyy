package com.moodi.task.ui.viewmodel

import app.cash.turbine.test
import com.moodi.domain.model.GiphyAppModel
import com.moodi.domain.usecase.RandomGiphyUseCase
import com.moodi.domain.util.Resource
import com.moodi.task.dispatcher.PeriodicDispatcher
import com.moodi.task.sate.random.RandomState
import com.moodi.task.viewmodel.RandomViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * This class is used to test the [RandomViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RandomViewModelTest {
    private lateinit var viewModel: RandomViewModel
    private val randomUseCase: RandomGiphyUseCase = mockk()

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

    private fun provideDispatcherProvider(): PeriodicDispatcher {
        return PeriodicDispatcher(dispatcher)
    }

    @Test
    fun `randomGif() should return success State with data`() = runTest {

        every { randomUseCase() } returns flow {
            emit(
                Resource.Success(
                    GiphyAppModel(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                    )
                )
            )
        }



        viewModel = RandomViewModel(randomUseCase, provideDispatcherProvider())
        viewModel.randomGif()
        viewModel.dataState.test {
            // assert if SearchState.Empty
            val awaitEmpty = awaitItem()
            assert(awaitEmpty is RandomState.Empty)

            // assert if SearchState.Loading
            val awaitLoading = awaitItem()
            assert(awaitLoading is RandomState.Loading)

            // assert if SearchState.Success
            val awaitSuccess = awaitItem()
            assert(awaitSuccess is RandomState.Success)

        }
    }

/*
    @Test
    fun `randomGif() should return error State`() = runTest {
        val fakeRepository = FakeRepository(
            null,
            Resource.Failure(
                ErrorCode.SERVER_ERROR,
                ""
            )
        )
        viewModel = RandomViewModel(fakeRepository, provideDispatcherProvider())
        viewModel.randomGif()
        viewModel.dataState.test {
            // assert SearchState.Empty
            val awaitEmpty = awaitItem()
            assert(awaitEmpty is RandomState.Empty)

            // assert if it is SearchState.Loading
            val awaitLoading = awaitItem()
            assert(awaitLoading is RandomState.Loading)

            // assert if SearchState.NetworkError
            val awaitSuccess = awaitItem()
            assert(awaitSuccess is RandomState.NetworkError)

            // assert if SearchState.NetworkError has error code 500
            assert((awaitSuccess as RandomState.NetworkError).errorCode == ErrorCode.SERVER_ERROR)
        }
    }
*/

/*
    @Test
    fun `generateRandomGif() should return success State with data`() = runTest {
        val fakeRepository = FakeRepository(
            null,
            Resource.Success(
                mockUtil
                    .convertJsonToString(MockUtil.RANDOM_MODEL)
                    .fromJson<RandomGiphyModelResponse>()
                    .asAppModel()
            )
        )

        val periodicDispatcher = provideDispatcherProvider()
        viewModel = RandomViewModel(fakeRepository, periodicDispatcher)
        viewModel.generateRandomGif()
//        dispatcher.scheduler.advanceUntilIdle()
        viewModel.dataState.test {
            // assert if SearchState.Empty
            val awaitEmpty = awaitItem()
            assert(awaitEmpty is RandomState.Empty)

            // assert if SearchState.Loading
            val awaitLoading = awaitItem()
            assert(awaitLoading is RandomState.Loading)

            // assert if SearchState.Success
            val awaitSuccess = awaitItem()
            assert(awaitSuccess is RandomState.Success)

            // assert verify data

            val expectedData = API_DATA_RANDOM
            assert((awaitSuccess as RandomState.Success).data == expectedData)
            periodicDispatcher.stop()
        }
    }
*/
}

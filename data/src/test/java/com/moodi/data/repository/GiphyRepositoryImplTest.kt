package com.moodi.data.repository

import app.cash.turbine.test
import com.moodi.common.ErrorCode
import com.moodi.data.mock.FakeDataSource
import com.moodi.domain.util.Resource
import com.moodi.data.mock.MockUtil
import com.moodi.data.mock.fromJson
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Before
import org.junit.Test

/**
 * This class is used to test the [GiphyRepositoryImpl] again [FakeDataSource].
 */
class GiphyRepositoryImplTest {

    private lateinit var remoteDataSource: FakeDataSource
    private var coroutineScheduler = TestCoroutineScheduler()
    private lateinit var sut: GiphyRepositoryImpl
    private var mockUtil = MockUtil()

    @Before
    fun setUp() {
        remoteDataSource = FakeDataSource()
        sut = GiphyRepositoryImpl(remoteDataSource, coroutineScheduler)
    }

    @Test
    fun `getRandomGif return success`() = runBlocking {
        remoteDataSource.testGetRandomGif = Resource.Success(
            mockUtil.convertJsonToString(MockUtil.RANDOM_MODEL).fromJson()
        )
        val result = sut.getRandomGif()
        result.test {
            val loading = awaitItem()
            assert(loading is Resource.Loading)

            val success = awaitItem()
            assert(success is Resource.Success)

            awaitComplete()
        }
    }

    @Test
    fun `getRandomGif return failure`() = runBlocking {
        remoteDataSource.testGetRandomGif = Resource.Failure(
            ErrorCode.SERVER_ERROR,
            ""
        )

        val result = sut.getRandomGif()
        result.test {
            val loading = awaitItem()
            assert(loading is Resource.Loading)

            val failure = awaitItem()
            assert(failure is Resource.Failure)

            awaitComplete()
        }
    }

    @Test
    fun `searchGif return success`() = runBlocking {
        remoteDataSource.testSearchGif = Resource.Success(
            mockUtil.convertJsonToString(MockUtil.SEARCH_MODEL).fromJson()

        )


        val result = sut.searchGif("test", 5)
        result.test {
            val loading = awaitItem()
            assert(loading is Resource.Loading)

            val success = awaitItem()
            assert(success is Resource.Success)

            awaitComplete()
        }
    }

    @Test
    fun `searchGif return failure`() = runBlocking {

        remoteDataSource.testSearchGif = Resource.Failure(
            ErrorCode.SERVER_ERROR,
            ""
        )

        val result = sut.searchGif("test", 5)
        result.test {
            val loading = awaitItem()
            assert(loading is Resource.Loading)

            val failure = awaitItem()
            assert(failure is Resource.Failure)

            awaitComplete()
        }
    }

}
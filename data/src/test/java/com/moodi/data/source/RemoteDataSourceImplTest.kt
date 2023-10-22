package com.moodi.data.source

import com.moodi.data.mock.ErrorCode.Companion.FAILURE_ERROR
import com.moodi.data.mock.ErrorCode.Companion.SERVER_ERROR
import com.moodi.data.mock.ErrorCode.Companion.SUCCESS_CODE
import com.moodi.domain.util.ERROR_NETWORK
import com.moodi.domain.util.Resource
import com.moodi.task.mock.FakeWebServerImplementation
import com.moodi.task.mock.MockUtil
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * This class is used to test the [RemoteDataSourceImpl]. It uses [FakeWebServerImplementation]
 * to mock the web server.
 */
class RemoteDataSourceImplTest : FakeWebServerImplementation() {

    private lateinit var sut: RemoteDataSourceImpl

    @Before
    fun setup() {
        sut = RemoteDataSourceImpl(fakeWebServiceAPI)
    }

    @Test
    fun `getRandomGif() returns Resource Success`() = runTest {
        mockWebServer.url("/v1/gifs/random")
        mockWebServer.enqueueResponse(MockUtil.RANDOM_MODEL, SUCCESS_CODE)
        val result = sut.getRandomGif()
        assert(result is Resource.Success)
    }

    @Test
    fun `getRandomGif() returns Resource Error`() = runTest {
        mockWebServer.url("/v1/gifs/random")
        mockWebServer.enqueueResponse(MockUtil.RANDOM_MODEL, SERVER_ERROR)
        val result = sut.getRandomGif()
        assert(result is Resource.Failure)
    }

    @Test
    fun `getRandomGif() returns Resource Network Error`() = runTest {
        mockWebServer.enqueueResponseFail(MockUtil.RANDOM_MODEL, FAILURE_ERROR)
        val result = sut.getRandomGif()
        assert(result is Resource.Failure)
        assert(result.errorCode == ERROR_NETWORK)
    }

    @Test
    fun `searchGif() returns Resource Success`() = runTest {
        mockWebServer.enqueueResponse(MockUtil.SEARCH_MODEL, SUCCESS_CODE)
        val result = sut.searchGif(SEARCH_QUERY, 5)
        // assert if it is Resource.Success
        assert(result is Resource.Success)
    }

    @Test
    fun `searchGif() returns Resource Error`() = runTest {
        mockWebServer.enqueueResponse(MockUtil.SEARCH_MODEL, SERVER_ERROR)
        val result = sut.searchGif(SEARCH_QUERY, 5)
        assert(result is Resource.Failure)
    }

    @Test
    fun `searchGif() returns Resource Network Error`() = runTest {
        mockWebServer.enqueueResponseFail(MockUtil.SEARCH_MODEL, FAILURE_ERROR)
        val result = sut.searchGif(SEARCH_QUERY, 5)
        assert(result is Resource.Failure)
        assert(result.errorCode == ERROR_NETWORK)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    companion object {
        const val SEARCH_QUERY = "test"
    }
}
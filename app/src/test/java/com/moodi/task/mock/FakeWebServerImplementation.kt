@file:Suppress("SameParameterValue")

package com.moodi.task.mock

import com.moodi.task.data.remote.api.WebApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

/**
 * This class is used to mock the behaviour of the web server and return the response accordingly
 */
@Suppress("SameParameterValue", "SameParameterValue")
open class FakeWebServerImplementation {

    protected var mockWebServer = MockWebServer()

    companion object {
        const val PATH = "api-response/"
    }

    private val client
        get() = OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS).writeTimeout(1, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    protected val fakeWebServiceAPI
        get() = Retrofit.Builder().baseUrl(mockWebServer.url("/")).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WebApi::class.java)

    /**
     * extension method to enqueue the response from the web server
     * @param fileName the name of the json file under [PATH]
     * @param code the response code to be returned
     */
    protected fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("$PATH$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    /**
     * enqueue the failed response from the web server
     * @param fileName the name of the json file under [PATH]
     * @param code the response code to be returned
     */
    protected fun MockWebServer.enqueueResponseFail(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("$PATH/$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

}
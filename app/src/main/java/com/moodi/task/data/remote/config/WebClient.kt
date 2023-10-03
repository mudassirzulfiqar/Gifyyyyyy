package com.moodi.task.data.remote.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class is used to create the Retrofit WebClient
 * The WebClient is used to make the network calls
 * The WebClient is configured with the API_KEY, [HttpLoggingInterceptor] and [GsonConverterFactory]
 *
 * returns Retrofit
 */
class WebClient {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor { apiKeyAsQuery(it) }
                .addInterceptor(httpDebugger())
                .build()
            )
            .baseUrl(Endpoint.MOBILE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}
package com.moodi.task.data.remote.config

import com.moodi.task.BuildConfig
import okhttp3.Interceptor

const val API_KEY = "api_key"

/**
 * This method is used to add the api key as query parameter
 * The BuildConfig.API_KEY is configured from build.gradle file
 *
 * returns [Interceptor]
 *
 * @param chain [Interceptor.Chain]
 */
fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
    chain.request()
        .newBuilder()
        .url(
            chain
                .request()
                .url.newBuilder()
                .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                .build()
        )
        .build()
)

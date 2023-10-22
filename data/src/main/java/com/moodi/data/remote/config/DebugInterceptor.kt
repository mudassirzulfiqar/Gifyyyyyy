package com.moodi.data.remote.config

import okhttp3.logging.HttpLoggingInterceptor

/**
 * This method is used to add LoggingInterceptor to Retrofit WebClient
 * Current level is set to BASIC
 * returns [HttpLoggingInterceptor]
 */
fun httpDebugger(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
}


package com.moodi.data.remote.config

/**
 * This class is used to store the Endpoints and Routes
 */
class Endpoint {
    companion object {
        /**
         * Base URL for the API
         */
        private const val BASE_URL = "https://api.giphy.com/"

        /**
         *  API version
         */
        private const val VERSION = "v1/"

        /**
         * API path
         */
        private const val GIFS = "gifs/"

        /**
         * API URL used in WebClient @see  WebClient
         */
        const val MOBILE_API_URL = BASE_URL + VERSION + GIFS
    }

    class Route {
        companion object {
            const val RANDOM = "random/"
            const val SEARCH = "search/"
        }
    }
}
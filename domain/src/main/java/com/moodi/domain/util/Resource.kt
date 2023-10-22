package com.moodi.domain.util

const val ERROR_NETWORK = -1

/**
 * A generic wrapper class around data request to handle success, failure and loading.
 */
sealed class Resource<T>(
    val data: T? = null, val errorCode: Int? = null, val errorMessage: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T> : Resource<T>(null)
    class Failure<T>(errorCode: Int, message: String) :
        Resource<T>(null, errorCode, errorMessage = message)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$errorCode]"
            is Loading -> "Loading"
        }
    }
}

package com.tobianoapps.zeldareference.util.networking

/**
 * Wrapper class to observe api call states
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
    val time: Long? = null
) {
    class Success<T>(
        data: T,
        message: String? = null,
        code: Int? = null,
        time: Long? = null
    ) : Resource<T>(data, null, code, time)

    class Error<T>(
        message: String,
        code: Int? = null,
        time: Long? = null,
        data: T? = null
    ) : Resource<T>(data, message, code, time)

    class Loading<T> : Resource<T>()

    class Canceled<T>(message: String, data: T? = null) : Resource<T>(data, message)

}

package com.tobianoapps.zeldareference.util.networking

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * This class handles exceptions that are NOT propagated to the caller by okhttp by default
 */
class ErrorInterceptor : Interceptor {

    @Throws(Exception::class)
    @Suppress("TooGenericExceptionCaught")
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            val bodyString = response.body!!.string()

            return response.newBuilder()
                .body(bodyString.toResponseBody(response.body?.contentType()))
                .build()
        } catch (e: IOException) {
            /**
             * If any of the exceptions below occur, they will be caught here and propagated to
             * the caller.
             */
            when (e) {
                is SocketTimeoutException -> {
                    throw SocketTimeoutException()
                }
                is UnknownHostException -> {
                    throw UnknownHostException()
                }
                is ConnectionShutdownException -> {
                    throw ConnectionShutdownException()
                }
                else -> throw e
            }
        }
    }
}






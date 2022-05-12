package com.example.habits.net

import android.util.Log
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


private const val TOKEN = "c170f207-1e17-4b15-925d-f396ec57fbcc"

class AuthInterceptor : Interceptor {

    companion object {
        private const val authHeader = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder().header(
            authHeader,
            TOKEN,
        )
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}

class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response = chain.proceed(request)

        var tryCount = 0
        while (!response.isSuccessful) {
            Log.i(RetryInterceptor::class.java.name, "Request is not successful - $tryCount")
            tryCount += 1

            response = chain.proceed(request)
        }
        return response
    }
}
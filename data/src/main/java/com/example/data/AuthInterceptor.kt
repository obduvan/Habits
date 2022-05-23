package com.example.data

import okhttp3.Interceptor
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

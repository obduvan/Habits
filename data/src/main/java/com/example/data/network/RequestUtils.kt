package com.example.data.network

import android.util.Log
import kotlinx.coroutines.delay

suspend fun <T> retryRequest(delay: Long = 1000, func: suspend () -> T): T {
    while(true) {
        try {
            return func()
        } catch (e: Exception) {
            Log.e("RetryRequest", Thread.currentThread().id.toString(), e)
        }
        delay(delay)
    }
}
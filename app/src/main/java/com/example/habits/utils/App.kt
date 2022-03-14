package com.example.habits.utils

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private var instance: Application? = null

        fun getInstance(): Application? = instance

        fun getContext(): Context? = instance?.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
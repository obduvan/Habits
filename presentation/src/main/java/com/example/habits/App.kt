package com.example.habits

import android.app.Application
import android.content.Context
import com.example.habits.di.AppComponent
import com.example.habits.di.DaggerAppComponent


class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        private var instance: Application? = null

        fun getInstance(): Application? = instance

        fun getContext(): Context? = instance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .context(context = this)
            .build()

        instance = this
    }
}
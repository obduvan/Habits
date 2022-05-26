package com.example.habits

import android.app.Application
import android.content.Context
import com.example.data.repository.HabitRepositoryImpl
import com.example.data.database.AppDatabase
import com.example.data.di.HabitsModule
import com.example.domain.useCases.DoneHabitUseCase
import com.example.domain.useCases.HabitsUseCase
import com.example.habits.di.AppComponent
import com.example.habits.di.DaggerAppComponent


class App : Application() {


    lateinit var appComponent: AppComponent
        private set

//    val database by lazy { AppDatabase.getDatabase(this) }
//    val habitsUseCase: HabitsUseCase by lazy {
//        HabitsUseCase(
//            HabitRepositoryImpl(database.habitDao())
//        )
//    }
//    val doneHabitUseCase: DoneHabitUseCase by lazy {
//        DoneHabitUseCase(
//            HabitRepositoryImpl(database.habitDao())
//        )
//    }

    companion object {
        private var instance: Application? = null

        fun getInstance(): Application? = instance

        fun getContext(): Context? = instance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .habitsModule(HabitsModule(this))
            .build()

        instance = this
    }
}
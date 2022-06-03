package com.example.habits.di

import android.content.Context
import com.example.data.di.DataModule
import com.example.domain.useCases.DoneHabitUseCase
import com.example.domain.useCases.HabitsUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun habitsComponent(): HabitsComponent.Builder

    fun editHabitComponent(): EditHabitComponent.Builder

    fun getHabitUseCase(): HabitsUseCase

    fun getDoneHabitUseCase(): DoneHabitUseCase
}
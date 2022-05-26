package com.example.habits.di

import com.example.data.di.HabitsModule
import com.example.domain.useCases.DoneHabitUseCase
import com.example.domain.useCases.HabitsUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitsModule::class])
interface AppComponent {

    fun getHabitUseCase(): HabitsUseCase

    fun getDoneHabitUseCase(): DoneHabitUseCase
}
package com.example.habits.di

import com.example.habits.habits.HabitsFragment
import dagger.Subcomponent
import javax.inject.Scope

@Habits
@Subcomponent
interface HabitsComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): HabitsComponent
    }

    fun inject(habitsFragment: HabitsFragment)
}

@Scope
annotation class Habits
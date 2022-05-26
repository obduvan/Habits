package com.example.habits.di

import com.example.habits.editHabit.EditHabitFragment
import dagger.Subcomponent
import javax.inject.Scope

@EditHabit
@Subcomponent
interface EditHabitComponent {

    @Subcomponent.Builder
    interface Builder{
        fun build(): EditHabitComponent
    }

    fun inject(editHabitFragment: EditHabitFragment)

}


@Scope
annotation class EditHabit

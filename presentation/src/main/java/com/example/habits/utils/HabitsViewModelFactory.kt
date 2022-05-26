package com.example.habits.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCases.DoneHabitUseCase
import com.example.domain.useCases.HabitsUseCase

import com.example.habits.editHabit.EditHabitViewModel
import com.example.habits.habits.HabitsViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class HabitsViewModelFactory @AssistedInject constructor(
    private val habitsUseCase: HabitsUseCase,
    private val doneHabitUseCase: DoneHabitUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            HabitsViewModel::class.java -> {
                HabitsViewModel(habitsUseCase, doneHabitUseCase)
            }

            EditHabitViewModel::class.java -> {
                EditHabitViewModel(habitsUseCase)
            }
            else -> throw IllegalStateException("Unknown ViewModel class.")
        }

        return viewModel as T
    }

    @AssistedFactory
    interface Factory {
        fun create(): HabitsViewModelFactory
    }
}
